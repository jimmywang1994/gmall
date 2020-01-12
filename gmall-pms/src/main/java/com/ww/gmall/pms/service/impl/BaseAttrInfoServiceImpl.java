package com.ww.gmall.pms.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ww.gmall.pms.bean.BaseAttrInfo;
import com.ww.gmall.pms.bean.BaseAttrValue;
import com.ww.gmall.pms.mapper.BaseAttrInfoMapper;
import com.ww.gmall.pms.mapper.BaseAttrValueMapper;
import com.ww.gmall.pms.service.BaseAttrInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 属性表 服务实现类
 * </p>
 *
 * @author wwei
 * @since 2020-01-12
 */
@Service
public class BaseAttrInfoServiceImpl extends ServiceImpl<BaseAttrInfoMapper, BaseAttrInfo> implements BaseAttrInfoService {

    @Autowired
    BaseAttrInfoMapper baseAttrInfoMapper;

    @Autowired
    BaseAttrValueMapper baseAttrValueMapper;
    @Override
    public List<BaseAttrInfo> baseAttrInfos(String catalog3Id) {
        QueryWrapper<BaseAttrInfo> wrapper=new QueryWrapper<>();
        wrapper.eq("catalog3_id",catalog3Id);
        return baseAttrInfoMapper.selectList(wrapper);
    }

    @Override
    public String saveAttr(BaseAttrInfo baseAttrInfo) {
        baseAttrInfoMapper.insert(baseAttrInfo);
        for(BaseAttrValue attrValue:baseAttrInfo.getAttrValueList()){
            attrValue.setAttrId(baseAttrInfo.getId());
            baseAttrValueMapper.insert(attrValue);
        }
        return "success";
    }
}
