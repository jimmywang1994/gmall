package com.ww.gmall.search;

import com.ww.gmall.pms.bean.SearchSkuInfo;
import com.ww.gmall.pms.bean.SkuInfo;
import io.searchbox.client.JestClient;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.http.HttpMessageConvertersAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.openfeign.FeignAutoConfiguration;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

@EnableFeignClients
@EnableEurekaClient
@Import({ FeignAutoConfiguration.class, HttpMessageConvertersAutoConfiguration.class })
@RunWith(SpringRunner.class)
@SpringBootTest
public class SearchTestApplication {
    @Test
    public void contextLoads(){
    }
}
