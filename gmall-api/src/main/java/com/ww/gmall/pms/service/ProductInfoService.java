package com.ww.gmall.pms.service;

import com.ww.gmall.pms.bean.ProductInfo;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author wwei
 * @since 2020-01-12
 */
public interface ProductInfoService extends IService<ProductInfo> {
    List<ProductInfo> produceInfos(String catalog3Id);

    String saveSpuInfo(ProductInfo productInfo);

    String uploadFile(MultipartFile multipartFile);
}
