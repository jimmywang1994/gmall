package com.ww.gmall.pms.mapper;

import com.ww.gmall.pms.bean.BaseAttrInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Set;

/**
 * <p>
 * 属性表 Mapper 接口
 * </p>
 *
 * @author wwei
 * @since 2020-01-12
 */
public interface BaseAttrInfoMapper extends BaseMapper<BaseAttrInfo> {

    List<BaseAttrInfo> getAttrValueByAttrId(@Param("valueIdStr") String valueIdStr);
}
