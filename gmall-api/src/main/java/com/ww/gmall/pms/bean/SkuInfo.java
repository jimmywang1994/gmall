package com.ww.gmall.pms.bean;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 库存单元表
 * </p>
 *
 * @author wwei
 * @since 2020-01-12
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("pms_sku_info")
@ApiModel(value="SkuInfo对象", description="库存单元表")
public class SkuInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "库存id(itemID)")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "商品id")
    @TableField("product_id")
    private Long productId;

    @ApiModelProperty(value = "价格")
    @TableField("price")
    private Double price;

    @ApiModelProperty(value = "sku名称")
    @TableField("sku_name")
    private String skuName;

    @ApiModelProperty(value = "商品规格描述")
    @TableField("sku_desc")
    private String skuDesc;

    @TableField("weight")
    private Double weight;

    @ApiModelProperty(value = "品牌(冗余)")
    @TableField("tm_id")
    private Long tmId;

    @ApiModelProperty(value = "三级分类id（冗余)")
    @TableField("catalog3_id")
    private Long catalog3Id;

    @ApiModelProperty(value = "默认显示图片(冗余)")
    @TableField("sku_default_img")
    private String skuDefaultImg;

    //sku基本属性值
    @TableField(exist = false)
    private List<SkuAttrValue> skuAttrValueList;

    //sku销售属性值
    @TableField(exist = false)
    private List<SkuSaleAttrValue> skuSaleAttrValueList;

}
