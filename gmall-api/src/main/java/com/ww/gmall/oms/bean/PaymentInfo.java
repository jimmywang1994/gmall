package com.ww.gmall.oms.bean;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("payment_info")
@ApiModel(value="Order对象", description="订单表")
public class PaymentInfo implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "编号")
    @TableId(value = "id", type = IdType.AUTO)
    private BigDecimal id;

    @ApiModelProperty(value = "对外业务编号")
    @TableField("order_sn")
    private String orderSn;

    @ApiModelProperty(value = "订单编号")
    @TableField("order_id")
    private String orderId;

    @ApiModelProperty(value = "支付宝交易编号")
    @TableField("alipay_trade_no")
    private String alipayTradeNo;

    @ApiModelProperty(value = "支付金额")
    @TableField("total_amount")
    private BigDecimal totalAmount;

    @ApiModelProperty(value = "交易内容")
    @TableField("subject")
    private String subject;

    @ApiModelProperty(value = "支付状态")
    @TableField("payment_status")
    private String paymentStatus;

    @ApiModelProperty(value = "提交时间")
    @TableField("create_time")
    private Date createTime;

    @ApiModelProperty(value = "创建时间")
    @TableField("confirm_time")
    private Date confirmTime;

    @ApiModelProperty(value = "支付状态")
    @TableField("callback_content")
    private String callbackContent;

    @ApiModelProperty(value = "创建时间")
    @TableField("callback_time")
    private Date callbackTime;
}
