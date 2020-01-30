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
 * 库存单元图片表
 * </p>
 *
 * @author wwei
 * @since 2020-01-12
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("pms_sku_image")
@ApiModel(value="SkuImage对象", description="库存单元图片表")
public class SkuImage implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "编号")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "商品id")
    @TableField("sku_id")
    private String skuId;

    @ApiModelProperty(value = "图片名称（冗余）")
    @TableField("img_name")
    private String imgName;

    @ApiModelProperty(value = "图片路径(冗余)")
    @TableField("img_url")
    private String imgUrl;

    @ApiModelProperty(value = "商品图片id")
    @TableField("product_img_id")
    private Long productImgId;

    @ApiModelProperty(value = "是否默认")
    @TableField("is_default")
    private String isDefault;


}
