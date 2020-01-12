package com.ww.gmall.pms.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ww.gmall.pms.bean.ProductImage;
import com.ww.gmall.pms.mapper.ProductImageMapper;
import com.ww.gmall.pms.service.ProductImageService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 商品图片表 服务实现类
 * </p>
 *
 * @author wwei
 * @since 2020-01-12
 */
@Service
public class ProductImageServiceImpl extends ServiceImpl<ProductImageMapper, ProductImage> implements ProductImageService {

}
