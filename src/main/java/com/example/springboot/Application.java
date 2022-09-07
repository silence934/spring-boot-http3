package com.example.springboot;

import com.example.springboot.vo.TestVO;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.server.reactive.AbstractServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ServerWebExchange;
import reactor.netty.channel.ChannelOperations;

/**
 * @author silence
 */
@RestController
@SpringBootApplication
public class Application {


    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }


    @GetMapping("/api")
    public String test(ServerWebExchange exchange) {
        AbstractServerHttpRequest request = (AbstractServerHttpRequest) exchange.getRequest();
        ServerHttpResponse response = exchange.getResponse();
        response.getHeaders().add("alt-svc", "h3-29=\":443\"; ma=3600");
        ChannelOperations operations = request.getNativeRequest();
        //ChannelUtil.printChannel(operations.channel());
        String name = Thread.currentThread().getName();
        //System.out.println(name);
        return "{\n" +
                "\"name\": \"张三\",\n" +
                "\"age\": 1,\n" +
                "\"threadName\": \"" + name + "\",\n" +
                "\"operations\": \"" + operations.getClass().getSimpleName() + "\"\n" +
                "}";
    }

    @PostMapping("/api/post")
    public TestVO postTest(@RequestBody TestVO vo, Long id) {
        TestVO testVO = new TestVO();
        testVO.setCode(vo.getCode());
        testVO.setMessage(vo.getMessage());
        testVO.setData(id);
        return testVO;
    }


    @PostMapping("/api/formdata")
    public TestVO formData(TestVO vo, ServerWebExchange exchange) {
        Object operations = ((AbstractServerHttpRequest) exchange.getRequest()).getNativeRequest();
        //ChannelUtil.printChannel(((DisposableChannel) operations).channel());
        vo.setOperationsName(operations.getClass().getSimpleName());
        vo.setFileName(vo.getFile().filename());
        vo.setFile(null);
        return vo;
    }


    @GetMapping("/api/params")
    public TestVO params(TestVO vo, ServerWebExchange exchange) {
        Object operations = ((AbstractServerHttpRequest) exchange.getRequest()).getNativeRequest();
        vo.setOperationsName(operations.getClass().getSimpleName());
        return vo;
    }


}
