package com.ww.gmall.user.service.impl;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ww.gmall.ums.bean.UmsMemberRuleSetting;
import com.ww.gmall.user.mapper.UmsMemberRuleSettingMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 会员积分成长规则表 服务实现类
 * </p>
 *
 * @author wwei
 * @since 2020-01-09
 */
@Service
public class UmsMemberRuleSettingServiceImpl extends ServiceImpl<UmsMemberRuleSettingMapper, UmsMemberRuleSetting> implements IService<UmsMemberRuleSetting> {

}
