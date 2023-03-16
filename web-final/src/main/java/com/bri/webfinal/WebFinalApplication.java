package com.bri.webfinal;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.bri.webfinal.mapper")
public class WebFinalApplication {

    public static void main(String[] args) {
        SpringApplication.run(WebFinalApplication.class, args);
    }

}
