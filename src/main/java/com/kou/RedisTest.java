package com.kou;

import com.alibaba.fastjson.JSONObject;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Transaction;

/**
 * @author JIAJUN KOU
 */
public class RedisTest {
    public static void main(String[] args) {
        Jedis jedis = RedisUtils.getJedis();
        System.out.println(jedis.ping());
        //事务操作
        JSONObject jsonObject=new JSONObject();
        jsonObject.put("hello","wang");
        jsonObject.put("name","hou");
        //开启事务
        Transaction multi = jedis.multi();
        String str = jsonObject.toJSONString();
        try {
            multi.set("user1",str);
            multi.set("user2",str);

            multi.exec();
        } catch (Exception e) {
            //放弃事务
            multi.discard();
            e.printStackTrace();
        }finally {
            System.out.println(jedis.get("user1"));
            System.out.println(jedis.get("user2"));
            RedisUtils.close(jedis);

        }

    }
}
