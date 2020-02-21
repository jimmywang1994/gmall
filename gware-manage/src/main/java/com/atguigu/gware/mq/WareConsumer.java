package com.atguigu.gware.mq;

import com.alibaba.fastjson.JSON;

import com.atguigu.gware.util.ActiveMQUtil;
import com.atguigu.gware.enums.TaskStatus;
import com.atguigu.gware.mapper.WareOrderTaskDetailMapper;
import com.atguigu.gware.mapper.WareOrderTaskMapper;
import com.atguigu.gware.mapper.WareSkuMapper;
import com.atguigu.gware.service.GwareService;

import com.ww.gmall.oms.bean.Order;
import com.ww.gmall.oms.bean.OrderItem;
import com.ww.gmall.oms.bean.WareOrderTask;
import com.ww.gmall.oms.bean.WareOrderTaskDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.TextMessage;
import java.util.*;

/**
 * @param
 * @return
 */
@Component
public class WareConsumer {

    @Autowired
    WareOrderTaskMapper wareOrderTaskMapper;

    @Autowired
    WareOrderTaskDetailMapper wareOrderTaskDetailMapper;

    @Autowired
    WareSkuMapper wareSkuMapper;

    @Autowired
    ActiveMQUtil activeMQUtil;

    @Autowired
    JmsTemplate jmsTemplate;

    @Autowired
    GwareService gwareService;

    @JmsListener(destination = "ORDER_PAY_QUEUE", containerFactory = "jmsQueueListener")
    public void receiveOrder(TextMessage textMessage) throws JMSException {
        String orderTaskJson = textMessage.getText();

        /***
         * 转化并保存订单对象
         */
        Order orderInfo = JSON.parseObject(orderTaskJson, Order.class);

        // 将order订单对象转为订单任务对象
        WareOrderTask wareOrderTask = new WareOrderTask();
        wareOrderTask.setConsignee(orderInfo.getReceiverName());
        wareOrderTask.setConsigneeTel(orderInfo.getReceiverPhone());
        wareOrderTask.setCreateTime(new Date());
        wareOrderTask.setDeliveryAddress(orderInfo.getReceiverDetailAddress());
        wareOrderTask.setOrderId(orderInfo.getId().toString());
        ArrayList<WareOrderTaskDetail> wareOrderTaskDetails = new ArrayList<>();

        // 打开订单的商品集合
        List<OrderItem> orderDetailList = orderInfo.getOrderItemList();
        for (OrderItem orderDetail : orderDetailList) {
            WareOrderTaskDetail wareOrderTaskDetail = new WareOrderTaskDetail();

            wareOrderTaskDetail.setSkuId(orderDetail.getProductSkuId().toString());
            wareOrderTaskDetail.setSkuName(orderDetail.getProductName());
            wareOrderTaskDetail.setSkuNum(orderDetail.getProductQuantity().intValue());
            wareOrderTaskDetails.add(wareOrderTaskDetail);

        }
        wareOrderTask.setDetails(wareOrderTaskDetails);
        wareOrderTask.setTaskStatus(TaskStatus.PAID.toString());
        gwareService.saveWareOrderTask(wareOrderTask);

        textMessage.acknowledge();

        // 检查该交易的商品是否有拆单需求
        List<WareOrderTask> wareSubOrderTaskList = gwareService.checkOrderSplit(wareOrderTask);// 检查拆单

        // 库存削减
        if (wareSubOrderTaskList != null && wareSubOrderTaskList.size() >= 2) {
            for (WareOrderTask orderTask : wareSubOrderTaskList) {
                gwareService.lockStock(orderTask);
            }
        } else {
            gwareService.lockStock(wareOrderTask);
        }


    }

}
