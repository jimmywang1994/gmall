package com.ww.gmall.pms.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ww.gmall.pms.bean.ProductLadder;
import com.ww.gmall.pms.mapper.ProductLadderMapper;
import com.ww.gmall.pms.service.ProductLadderService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 产品阶梯价格表(只针对同商品) 服务实现类
 * </p>
 *
 * @author wwei
 * @since 2020-01-12
 */
@Service
public class ProductLadderServiceImpl extends ServiceImpl<ProductLadderMapper, ProductLadder> implements ProductLadderService {

}
