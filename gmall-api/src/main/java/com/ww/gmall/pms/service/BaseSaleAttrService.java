package com.ww.gmall.pms.service;

import com.ww.gmall.pms.bean.BaseSaleAttr;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author wwei
 * @since 2020-01-12
 */
public interface BaseSaleAttrService extends IService<BaseSaleAttr> {
    List<BaseSaleAttr> baseSaleAttrs();
}
