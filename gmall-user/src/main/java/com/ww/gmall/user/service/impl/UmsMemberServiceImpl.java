package com.ww.gmall.user.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ww.gmall.config.RedisUtil;
import com.ww.gmall.ums.service.UmsMemberService;
import com.ww.gmall.ums.bean.UmsMember;
import com.ww.gmall.user.mapper.UmsMemberMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jodd.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import sun.reflect.generics.tree.ReturnType;

import java.util.List;

/**
 * <p>
 * 会员表 服务实现类
 * </p>
 *
 * @author wwei
 * @since 2020-01-09
 */
@Service
public class UmsMemberServiceImpl extends ServiceImpl<UmsMemberMapper, UmsMember> implements UmsMemberService {

    @Autowired
    UmsMemberMapper umsMemberMapper;
    @Autowired
    RedisUtil redisUtil;

    @Override
    public List<UmsMember> selectAllMember() {
        return umsMemberMapper.selectList(null);
    }

    @Override
    public UmsMember login(String userName, String passWord) {
        Jedis jedis = null;
        try {
            jedis = redisUtil.getJedis();
            if (jedis != null) {
                String userMemberStr = jedis.get("user:" + passWord + ":password");
                if (StringUtil.isNotBlank(userMemberStr)) {
                    //密码正确
                    UmsMember memberFromCache = JSON.parseObject(userMemberStr, UmsMember.class);
                    return memberFromCache;
                }
            }
            //链接redis失败，开启数据库
            UmsMember memberFromDb = loginFromDb(userName, passWord);
            if (memberFromDb != null) {
                jedis.setex("user:" + memberFromDb.getPassword() + ":info", 60 * 60 * 24, JSON.toJSONString(memberFromDb));
            }
            return memberFromDb;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            jedis.close();
        }
        return null;
    }

    @Override
    public void addToken(String token, String memberId) {
        Jedis jedis = null;
        try {
            jedis = redisUtil.getJedis();
            jedis.setex("user:" + memberId + ":token", 60 * 60 * 2, token);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            jedis.close();
        }
    }

    @Override
    public UmsMember addOauthUser(UmsMember umsMember) {
        umsMemberMapper.insert(umsMember);
        return checkOauthUser(umsMember.getSourceUid());
    }

    @Override
    public UmsMember checkOauthUser(String sourceUid) {
        QueryWrapper<UmsMember> wrapper = new QueryWrapper<>();
        wrapper.eq("source_uid", sourceUid);
        UmsMember umsMember = umsMemberMapper.selectOne(wrapper);
        return umsMember;
    }

    private UmsMember loginFromDb(String userName, String passWord) {
        QueryWrapper<UmsMember> wrapper = new QueryWrapper<>();
        wrapper.eq("username", userName);
        wrapper.eq("password", passWord);
        UmsMember member = umsMemberMapper.selectOne(wrapper);
        return member;
    }
}
