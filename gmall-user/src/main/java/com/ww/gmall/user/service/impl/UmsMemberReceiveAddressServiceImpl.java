package com.ww.gmall.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ww.gmall.ums.bean.UmsMemberReceiveAddress;
import com.ww.gmall.ums.service.UmsMemberReceiveAddressService;
import com.ww.gmall.user.mapper.UmsMemberReceiveAddressMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 会员收货地址表 服务实现类
 * </p>
 *
 * @author wwei
 * @since 2020-01-09
 */
@Service
public class UmsMemberReceiveAddressServiceImpl extends ServiceImpl<UmsMemberReceiveAddressMapper, UmsMemberReceiveAddress> implements UmsMemberReceiveAddressService {

    @Autowired
    UmsMemberReceiveAddressMapper umsMemberReceiveAddressMapper;

    @Override
    public List<UmsMemberReceiveAddress> umsMemberReceiveAddressList(String memberId) {
        QueryWrapper<UmsMemberReceiveAddress> wrapper=new QueryWrapper<>();
        wrapper.eq("member_id",memberId);
        List<UmsMemberReceiveAddress> umsMemberReceiveAddressList=umsMemberReceiveAddressMapper.selectList(wrapper);
        return umsMemberReceiveAddressList;
    }

    @Override
    public UmsMemberReceiveAddress umsMemberReceiveAddress(String receiveAddressId) {
        UmsMemberReceiveAddress umsMemberReceiveAddress=umsMemberReceiveAddressMapper.selectById(receiveAddressId);
        return umsMemberReceiveAddress;
    }
}
