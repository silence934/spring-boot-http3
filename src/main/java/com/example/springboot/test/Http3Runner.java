package com.example.springboot.test;

import io.netty.incubator.codec.quic.InsecureQuicTokenHandler;
import io.netty.incubator.codec.quic.QuicSslContext;
import io.netty.incubator.codec.quic.QuicSslContextBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.http.server.reactive.HttpHandler;
import org.springframework.stereotype.Component;
import reactor.netty.DisposableServer;
import xyz.nyist.adapter.ReactorHttp3HandlerAdapter;
import xyz.nyist.core.Http3;
import xyz.nyist.http.server.Http3Server;

import javax.net.ssl.KeyManagerFactory;
import java.net.InetSocketAddress;
import java.security.KeyStore;
import java.time.Duration;

/**
 * @author: silence
 * @Date: 2022/8/2 18:29
 * @Description:
 */
@Slf4j
@Component
public class Http3Runner implements CommandLineRunner , DisposableBean {


    @Autowired
    @SuppressWarnings("all")
    private HttpHandler httpHandler;

    private DisposableServer connection;

    @Override
    public void run(String... args) throws Exception {
        KeyStore keyStore = KeyStore.getInstance("PKCS12");
        keyStore.load(Http3Runner.class.getClassLoader().getResourceAsStream("http3.nyist.xyz.pfx"), "123456".toCharArray());
        KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
        keyManagerFactory.init(keyStore, "123456".toCharArray());

        QuicSslContext serverCtx = QuicSslContextBuilder.forServer(keyManagerFactory, "123456")
                .keylog(true)
                .applicationProtocols(Http3.supportedApplicationProtocols()).build();


        this.connection = Http3Server.create()
                //chrome 默认未开启 QpackDynamicTable
                .disableQpackDynamicTable(true)
                .tokenHandler(InsecureQuicTokenHandler.INSTANCE)
                .handleStream(new ReactorHttp3HandlerAdapter(httpHandler))
                .bindAddress(() -> new InetSocketAddress("0.0.0.0", 443))
                .wiretap(true)
                .secure(serverCtx)
                .idleTimeout(Duration.ofSeconds(5))
                .initialSettings(spec ->
                                         spec.maxData(10000000)
                                                 .maxStreamDataBidirectionalLocal(1000000)
                                                 .maxStreamDataBidirectionalRemote(1000000)
                                                 .maxStreamsBidirectional(100)
                                                 .maxStreamDataUnidirectional(1024)
                                                 .maxStreamsUnidirectional(3)
                ).bindNow();

    }


    @Override
    public void destroy() {
        connection.dispose();
    }

}
