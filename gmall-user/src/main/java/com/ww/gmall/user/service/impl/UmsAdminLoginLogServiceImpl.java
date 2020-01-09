package com.ww.gmall.user.service.impl;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ww.gmall.bean.UmsAdminLoginLog;
import com.ww.gmall.user.mapper.UmsAdminLoginLogMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 后台用户登录日志表 服务实现类
 * </p>
 *
 * @author wwei
 * @since 2020-01-09
 */
@Service
public class UmsAdminLoginLogServiceImpl extends ServiceImpl<UmsAdminLoginLogMapper, UmsAdminLoginLog> implements IService<UmsAdminLoginLog> {

}
