package com.ww.gmall.ums.service;

import com.ww.gmall.ums.bean.UmsMemberReceiveAddress;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 会员收货地址表 服务类
 * </p>
 *
 * @author wwei
 * @since 2020-01-09
 */
public interface UmsMemberReceiveAddressService extends IService<UmsMemberReceiveAddress> {
    List<UmsMemberReceiveAddress> umsMemberReceiveAddressList(String memberId);
}
