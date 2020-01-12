package com.ww.gmall.pms.service;

import com.ww.gmall.pms.bean.BaseCatalog1;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 一级分类表 服务类
 * </p>
 *
 * @author wwei
 * @since 2020-01-12
 */
public interface BaseCatalog1Service extends IService<BaseCatalog1> {
    List<BaseCatalog1> getCatalog1s();
}
