package com.ww.gmall.user.service.impl;

import com.ww.gmall.service.UmsMemberService;
import com.ww.gmall.bean.UmsMember;
import com.ww.gmall.user.mapper.UmsMemberMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * <p>
 * 会员表 服务实现类
 * </p>
 *
 * @author wwei
 * @since 2020-01-09
 */
@Component
@Service
public class UmsMemberServiceImpl extends ServiceImpl<UmsMemberMapper, UmsMember> implements UmsMemberService {

    @Autowired
    UmsMemberMapper umsMemberMapper;

    @Override
    public List<UmsMember> selectAllMember() {
        return umsMemberMapper.selectList(null);
    }
}
