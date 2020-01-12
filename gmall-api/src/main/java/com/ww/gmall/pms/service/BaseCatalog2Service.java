package com.ww.gmall.pms.service;

import com.ww.gmall.pms.bean.BaseCatalog2;
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
public interface BaseCatalog2Service extends IService<BaseCatalog2> {
    List<BaseCatalog2> getCatalog2s(String catalog1Id);
}
