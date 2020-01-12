package com.ww.gmall.pms.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ww.gmall.pms.bean.Brand;
import com.ww.gmall.pms.mapper.BrandMapper;
import com.ww.gmall.pms.service.BrandService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 品牌表 服务实现类
 * </p>
 *
 * @author wwei
 * @since 2020-01-12
 */
@Service
public class BrandServiceImpl extends ServiceImpl<BrandMapper, Brand> implements BrandService {

}
