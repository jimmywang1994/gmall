package com.ww.gmall.oms.bean;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.Date;
import java.util.List;

/**
 * @param
 * @return
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("ware_order_task")
public class WareOrderTask {

    @TableId(value = "id", type = IdType.AUTO)
    private String id ;
    @TableId(value = "order_id")
    private String orderId;
    @TableId(value = "consignee")
    private String consignee;
    @TableId(value = "consignee_tel")
    private String consigneeTel;
    @TableId(value = "delivery_address")
    private String deliveryAddress;
    @TableId(value = "order_comment")
    private String orderComment;
    @TableId(value = "payment_way")
    private String paymentWay;
    @TableId(value = "task_status")
    private String taskStatus;
    @TableId(value = "order_body")
    private String orderBody;
    @TableId(value = "tracking_no")
    private String trackingNo;
    @TableId(value = "create_time")
    private Date createTime;
    @TableId(value = "ware_id")
    private String wareId;
    @TableId(value = "task_comment")
    private String taskComment;
    @TableField(exist = false)
    private List<WareOrderTaskDetail> details;


}
