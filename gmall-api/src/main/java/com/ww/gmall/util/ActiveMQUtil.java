package com.ww.gmall.util;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.pool.PooledConnectionFactory;

import javax.jms.ConnectionFactory;

public class ActiveMQUtil {
    //连接池工厂
    PooledConnectionFactory pooledConnectionFactory=null;

    public ConnectionFactory init(String brokerUrl) {

        ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory(brokerUrl);
        //加入连接池
        pooledConnectionFactory=new PooledConnectionFactory(factory);
        //出现异常时重新连接
        pooledConnectionFactory.setReconnectOnException(true);
        //最大重试次数5次
        pooledConnectionFactory.setMaxConnections(5);
        //超时时间10000ms
        pooledConnectionFactory.setExpiryTimeout(10000);
        return pooledConnectionFactory;
    }

    public ConnectionFactory getConnectionFactory(){
        return pooledConnectionFactory;
    }

}
