package com.ww.gmall;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@EnableEurekaClient
@MapperScan("com.ww.gmall.payment.mapper")
@SpringBootApplication
public class GmallPaymentApplication {
    public static void main(String[] args) {
        SpringApplication.run(GmallPaymentApplication.class, args);
    }
}
