package com.ww.gmall.service;

import com.ww.gmall.bean.UmsMember;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 会员表 服务类
 * </p>
 *
 * @author wwei
 * @since 2020-01-09
 */
public interface UmsMemberService extends IService<UmsMember> {
    List<UmsMember> selectAllMember();
}
