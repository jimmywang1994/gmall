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
 * sku销售属性值
 * </p>
 *
 * @author wwei
 * @since 2020-01-12
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("pms_sku_sale_attr_value")
@ApiModel(value="SkuSaleAttrValue对象", description="sku销售属性值")
public class SkuSaleAttrValue implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "id")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "库存单元id")
    @TableField("sku_id")
    private Long skuId;

    @ApiModelProperty(value = "销售属性id（冗余)")
    @TableField("sale_attr_id")
    private String saleAttrId;

    @ApiModelProperty(value = "销售属性值id")
    @TableField("sale_attr_value_id")
    private String saleAttrValueId;

    @ApiModelProperty(value = "销售属性名称(冗余)")
    @TableField("sale_attr_name")
    private String saleAttrName;

    @ApiModelProperty(value = "销售属性值名称(冗余)")
    @TableField("sale_attr_value_name")
    private String saleAttrValueName;


}
