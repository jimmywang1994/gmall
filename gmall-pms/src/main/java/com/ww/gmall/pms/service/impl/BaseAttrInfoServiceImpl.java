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
import org.springframework.util.StringUtils;
//import org.apache.commons.lang.StringUtils;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

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
        QueryWrapper<BaseAttrInfo> wrapper = new QueryWrapper<>();
        wrapper.eq("catalog3_id", catalog3Id);
        List<BaseAttrInfo> baseAttrInfoList = baseAttrInfoMapper.selectList(wrapper);
        for (BaseAttrInfo baseAttrInfo : baseAttrInfoList) {
            QueryWrapper<BaseAttrValue> wrapper1 = new QueryWrapper<>();
            wrapper1.eq("attr_id", baseAttrInfo.getId());
            List<BaseAttrValue> attrValueList = baseAttrValueMapper.selectList(wrapper1);
            baseAttrInfo.setAttrValueList(attrValueList);
        }
        return baseAttrInfoList;
    }

    @Override
    public String saveAttr(BaseAttrInfo baseAttrInfo) {
        if (StringUtils.isEmpty(baseAttrInfo.getId())) {
            baseAttrInfoMapper.insert(baseAttrInfo);
            for (BaseAttrValue attrValue : baseAttrInfo.getAttrValueList()) {
                attrValue.setAttrId(baseAttrInfo.getId());
                baseAttrValueMapper.insert(attrValue);
            }
        } else {
            baseAttrInfoMapper.updateById(baseAttrInfo);
            QueryWrapper<BaseAttrValue> wrapper = new QueryWrapper<>();
            wrapper.eq("attr_id", baseAttrInfo.getId());
            baseAttrValueMapper.delete(wrapper);
            for (BaseAttrValue attrValue : baseAttrInfo.getAttrValueList()) {
                attrValue.setAttrId(baseAttrInfo.getId());
                baseAttrValueMapper.insert(attrValue);
            }
        }

        return "success";
    }

    @Override
    public String deleAttr(String attrId) {
        baseAttrInfoMapper.deleteById(attrId);
        QueryWrapper<BaseAttrValue> wrapper = new QueryWrapper<>();
        wrapper.eq("attr_id", attrId);
        baseAttrValueMapper.delete(wrapper);
        return "success";
    }

    @Override
    public List<BaseAttrInfo> getAttrValueByAttrId(Set<String> valueIdSet) {
        String valueIdStr = org.apache.commons.lang.StringUtils.join(valueIdSet, ",");
        List<BaseAttrInfo> attrValueByAttrId = baseAttrInfoMapper.getAttrValueByAttrId(valueIdStr);
        return attrValueByAttrId;
    }
}
