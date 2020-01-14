package com.ww.gmall.pms.client;

import com.ww.gmall.pms.bean.ProductInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@FeignClient("gmall-pms")
public interface ProductInfoService {
    @RequestMapping("/pms/product-info/spuList")
    public List<ProductInfo> productInfos(@RequestParam("catalog3Id") String catalog3Id);

    @RequestMapping("/pms/product-info/saveSpuInfo")
    public String saveSpuInfo(@RequestBody ProductInfo productInfo);

}
