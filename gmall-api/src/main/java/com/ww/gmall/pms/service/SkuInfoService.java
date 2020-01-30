package com.ww.gmall.pms.service;

import com.ww.gmall.pms.bean.SearchSkuInfo;
import com.ww.gmall.pms.bean.SkuInfo;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ww.gmall.pms.bean.SkuInfoParam;

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

    public SkuInfo skuById(String id, String ip);

    List<SkuInfo> getSkuSaleAttrValueListBySku(String productId);

    /**
     * 查询所有sku信息
     *
     * @return
     */
    List<SkuInfo> getAllSku(String catalog3Id);

}
