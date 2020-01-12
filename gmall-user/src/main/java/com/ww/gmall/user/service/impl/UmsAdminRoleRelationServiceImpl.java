package com.ww.gmall.user.service.impl;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ww.gmall.ums.bean.UmsAdminRoleRelation;
import com.ww.gmall.user.mapper.UmsAdminRoleRelationMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 后台用户和角色关系表 服务实现类
 * </p>
 *
 * @author wwei
 * @since 2020-01-09
 */
@Service
public class UmsAdminRoleRelationServiceImpl extends ServiceImpl<UmsAdminRoleRelationMapper, UmsAdminRoleRelation> implements IService<UmsAdminRoleRelation> {

}
