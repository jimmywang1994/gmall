package com.ww.gmall.user.service.impl;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ww.gmall.ums.bean.UmsMemberTag;
import com.ww.gmall.user.mapper.UmsMemberTagMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户标签表 服务实现类
 * </p>
 *
 * @author wwei
 * @since 2020-01-09
 */
@Service
public class UmsMemberTagServiceImpl extends ServiceImpl<UmsMemberTagMapper, UmsMemberTag> implements IService<UmsMemberTag> {

}
