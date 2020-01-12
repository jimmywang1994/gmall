package com.ww.gmall.pms.bean;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * sku平台属性值关联表
 * </p>
 *
 * @author wwei
 * @since 2020-01-12
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("pms_sku_attr_value")
@ApiModel(value="SkuAttrValue对象", description="sku平台属性值关联表")
public class SkuAttrValue implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "编号")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "属性id（冗余)")
    @TableField("attr_id")
    private Long attrId;

    @ApiModelProperty(value = "属性值id")
    @TableField("value_id")
    private Long valueId;

    @ApiModelProperty(value = "skuid")
    @TableField("sku_id")
    private Long skuId;


}
