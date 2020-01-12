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
 * 属性值表
 * </p>
 *
 * @author wwei
 * @since 2020-01-12
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("pms_base_attr_value")
@ApiModel(value="BaseAttrValue对象", description="属性值表")
public class BaseAttrValue implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "编号")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "属性值名称")
    @TableField("value_name")
    private String valueName;

    @ApiModelProperty(value = "属性id")
    @TableField("attr_id")
    private Long attrId;

    @ApiModelProperty(value = "启用：1 停用：0 1")
    @TableField("is_enabled")
    private String isEnabled;


}
