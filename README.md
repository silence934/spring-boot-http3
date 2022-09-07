# spring-boot-http3 


添加依赖:
```angular2html
        <dependency>
            <groupId>xyz.nyist</groupId>
            <artifactId>http3</artifactId>
            <version>1.0-SNAPSHOT</version>
            <exclusions>
                <exclusion>
                    <groupId>io.netty.incubator</groupId>
                    <artifactId>netty-incubator-codec-native-quic</artifactId>
                </exclusion>
            </exclusions>
        </dependency>

        <dependency>
            <groupId>io.netty.incubator</groupId>
            <artifactId>netty-incubator-codec-native-quic</artifactId>
            <version>0.0.28.Final</version>
            <!--使用与自己os匹配的classifier
                linux-x86_64
                osx-x86_64
                windows-x86_64
            -->
            <classifier>osx-x86_64</classifier>
            <scope>runtime</scope>
        </dependency>
```

http3依赖需要自己install  [http3](https://github.com/silence934/http3) 项目到本地仓库

http3必须使用tls，可以使用项目中的http3.nyist.xyz证书，这是一个正式的证书

http3服务是在com.example.springboot.test.Http3Runner中启动的,可以参考
