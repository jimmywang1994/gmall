package com.ww.gmall.redisson;

import com.ww.gmall.config.RedisUtil;
import jodd.util.StringUtil;
import org.hibernate.validator.internal.util.privilegedactions.LoadClass;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import redis.clients.jedis.Jedis;

@Controller
public class RedissonController {
    @Autowired
    RedisUtil redisUtil;
    @Autowired
    RedissonClient redissonClient;

    @RequestMapping("testRedisson")
    @ResponseBody
    public String testRedisson() {
        Jedis jedis = redisUtil.getJedis();
        RLock lock = redissonClient.getLock("lock");
        lock.lock();
        try {
            String v = jedis.get("k");
            if (StringUtil.isBlank(v)) {
                v = "1";
            }
            System.out.println(v);
            jedis.set("k", (Integer.parseInt(v) + 1) + "");
            jedis.close();
        }
        finally {
            lock.unlock();
        }
        return "success";
    }
}
