package com.ww.gmall.pms.service;

import com.ww.gmall.pms.bean.BaseAttrValue;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 属性值表 服务类
 * </p>
 *
 * @author wwei
 * @since 2020-01-12
 */
public interface BaseAttrValueService extends IService<BaseAttrValue> {
    List<BaseAttrValue> baseAttrValues(String attrId);
}
