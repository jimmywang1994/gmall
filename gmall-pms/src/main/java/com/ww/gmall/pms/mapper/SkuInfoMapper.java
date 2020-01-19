package com.ww.gmall.pms.mapper;

import com.ww.gmall.pms.bean.SkuInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 库存单元表 Mapper 接口
 * </p>
 *
 * @author wwei
 * @since 2020-01-12
 */
public interface SkuInfoMapper extends BaseMapper<SkuInfo> {
    List<SkuInfo> getSkuSaleAttrValueListBySku(@Param("productId") String productId);
}
