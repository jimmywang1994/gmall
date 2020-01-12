package com.ww.gmall.pms.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ww.gmall.pms.bean.BaseAttrValue;
import com.ww.gmall.pms.mapper.BaseAttrValueMapper;
import com.ww.gmall.pms.service.BaseAttrValueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 属性值表 服务实现类
 * </p>
 *
 * @author wwei
 * @since 2020-01-12
 */
@Service
public class BaseAttrValueServiceImpl extends ServiceImpl<BaseAttrValueMapper, BaseAttrValue> implements BaseAttrValueService {

    @Autowired
    BaseAttrValueMapper baseAttrValueMapper;
    @Override
    public List<BaseAttrValue> baseAttrValues(String attrId) {
        QueryWrapper<BaseAttrValue> wrapper=new QueryWrapper<>();
        wrapper.eq("attr_id",attrId);
        return baseAttrValueMapper.selectList(wrapper);
    }
}
