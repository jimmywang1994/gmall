package com.ww.gmall.oms.bean;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
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
@TableName("ware_sku")
public class WareSku {

    @TableId(value = "id", type = IdType.AUTO)
    private  String id ;
    @TableField("sku_id")
    private String skuId;
    @TableField("warehouse_id")
    private String warehouseId;
    @TableField("stock")
    private Integer stock=0;
    @TableField("stock_name")
    private  String stockName;
    @TableField("stock_locked")
    private Integer stockLocked;

    @TableField(exist = false)
    private  String warehouseName;

}
