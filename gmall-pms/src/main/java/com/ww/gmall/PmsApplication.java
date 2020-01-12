package com.ww.gmall;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EnableEurekaClient
@MapperScan("com.ww.gmall.pms.mapper")
@SpringBootApplication
public class PmsApplication {
    public static void main(String[] args) {
        SpringApplication.run(PmsApplication.class,args);
    }
}
