package com.ww.gmall.pms.service;

import com.ww.gmall.pms.bean.SkuInfo;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 库存单元表 服务类
 * </p>
 *
 * @author wwei
 * @since 2020-01-12
 */
public interface SkuInfoService extends IService<SkuInfo> {
    public String saveSkuInfo(SkuInfo skuInfo);

    public SkuInfo skuById(String id);

    List<SkuInfo> getSkuSaleAttrValueListBySku(String productId);
}
