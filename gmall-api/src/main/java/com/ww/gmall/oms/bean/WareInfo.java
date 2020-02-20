package com.ww.gmall.oms.bean;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @param
 * @return
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("wms_ware_info")
public class WareInfo {
    @TableId(value = "id", type = IdType.AUTO)
    private String id;
    @TableField("name")
    private String name;
    @TableField("address")
    private String address;
    @TableField("areacode")
    private String areacode;
}
