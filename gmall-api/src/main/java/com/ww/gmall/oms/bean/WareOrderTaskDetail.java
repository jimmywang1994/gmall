package com.ww.gmall.oms.bean;

import com.baomidou.mybatisplus.annotation.IdType;
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
@TableName("ware_order_task_detail")
public class WareOrderTaskDetail {

    @TableId(value = "id", type = IdType.AUTO)
    private String id;
    @TableId(value = "sku_id")
    private String skuId;
    @TableId(value = "sku_name")
    private String skuName;
    @TableId(value = "sku_nums")
    private Integer skuNum;
    @TableId(value = "task_id")
    private String taskId;

}
