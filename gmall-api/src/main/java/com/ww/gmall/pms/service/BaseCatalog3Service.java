package com.ww.gmall.pms.service;

import com.ww.gmall.pms.bean.BaseCatalog3;
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
public interface BaseCatalog3Service extends IService<BaseCatalog3> {
    List<BaseCatalog3> getCatalog3s(String catalog2Id);
}
