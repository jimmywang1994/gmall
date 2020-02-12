package com.ww.gmall.user.service.impl;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ww.gmall.ums.bean.UmsPermission;
import com.ww.gmall.ums.service.UmsPermissionService;
import com.ww.gmall.user.mapper.UmsPermissionMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 后台用户权限表 服务实现类
 * </p>
 *
 * @author wwei
 * @since 2020-01-09
 */
@Service
public class UmsPermissionServiceImpl extends ServiceImpl<UmsPermissionMapper, UmsPermission> implements UmsPermissionService {

}
