package com.ww.gmall.pms.service;

import com.ww.gmall.pms.bean.BaseAttrInfo;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 属性表 服务类
 * </p>
 *
 * @author wwei
 * @since 2020-01-12
 */
public interface BaseAttrInfoService extends IService<BaseAttrInfo> {
    List<BaseAttrInfo> baseAttrInfos(String catalog3Id);

}
