package com.sunray.common.util;

import com.sunray.common.constant.CacheKey;
import com.sunray.common.constant.DBConstant;
import org.apache.commons.lang3.StringUtils;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class JedisUtil {

    private static final JedisPool jedisPool;
    static {
        jedisPool = newJedisPool();
    }

    public static Jedis getJedis() {
        Jedis jedis= jedisPool.getResource();
        jedis.auth(DBConstant.REDIS_PASSWORD);
        jedis.select(DBConstant.REDIS_DB);
        return jedis;
    }

    private synchronized static JedisPool newJedisPool() {
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxTotal(DBConstant.REDIS_MAX_TOTAL);
        config.setMaxWaitMillis(DBConstant.REDIS_MAX_WAIT_MILLIS);
        config.setMaxIdle(DBConstant.REDIS_MAX_IDLE);
        config.setTestWhileIdle(true);
        return new JedisPool(config, DBConstant.REDIS_HOST, DBConstant.REDIS_PORT);
    }

    public static void setString(String key, String value, int time) {
        Jedis jedis = JedisUtil.getJedis();
        jedis.set(key, value);
        jedis.expire(key, time);
    }

    public static String getString(String key) {
        Jedis jedis = JedisUtil.getJedis();
        return jedis.get(key);
    }

    public static long delete(String key) {
        Jedis jedis = JedisUtil.getJedis();
        return jedis.del(key);
    }

    public static long lpush(String queueName, String...strings) {
        Jedis jedis = JedisUtil.getJedis();
        return jedis.lpush(queueName, strings);
    }

    public static long rpush(String queueName, String...strings) {
        Jedis jedis = JedisUtil.getJedis();
        return jedis.rpush(queueName, strings);
    }

    public static List<String> lrange(String queueName, long start, long end) {
        Jedis jedis = JedisUtil.getJedis();
        return jedis.lrange(queueName, start, end);
    }

    public static void lset(String queueName, long index, String value) {
        Jedis jedis = JedisUtil.getJedis();
        jedis.lset(queueName, index, value);
    }

    public static void hset(String key, String filed, String value) {
        Jedis jedis = JedisUtil.getJedis();
        jedis.hset(key, filed, value);
    }

    public static String hget(String key, String filed) {
        Jedis jedis = JedisUtil.getJedis();
        return jedis.hget(key, filed);
    }

    public static Map<String, String> hgetAll(String key) {
        Jedis jedis = JedisUtil.getJedis();
        return jedis.hgetAll(key);
    }

    public static void hdel(String key, String filed) {
        Jedis jedis = JedisUtil.getJedis();
        jedis.hdel(key, filed);
    }

    public static List<String> popQueue(String queueName, int count) {
        Jedis jedis = JedisUtil.getJedis();
        List<String> list = new LinkedList<String>();
        for (int i = 0; i < count; i++) {
            String value = jedis.lpop(queueName);
            if (!StringUtils.isEmpty(value)){
                list.add(value);
            }
        }
        return list;
    }



    public static void main(String[] args) {
        //JedisUtils.delAttrRedis(StaticVar.MESSAGE_QUEUE_NAME_1);

/*		MessageCmd sellMessage = new MessageCmd();
		sellMessage.setMsgType(2);
		sellMessage.setPopup(4);
		sellMessage.setBannerId(55);
		sellMessage.setFromUserId(3);
		//计算卖卡收益金额
		double sellProfit = DoubleUtil.mul(1, 6.0);
		sellMessage.setMessage("又卖出"+1+"张卡啦！小金库里又增添了￥"+sellProfit+"元哟~");
		sellMessage.setToUserId(276);
		sellMessage.setSendTime(20160114160711L);
		//将待处理的消息添加到redis队列
		JSONObject messageJo = JSONObject.fromObject(sellMessage);
		JedisUtils.pushQueue(StaticVar.MESSAGE_QUEUE_NAME_1, messageJo.toString());*/


		/*for (String str : JedisUtils.popQueue("xp", 2)) {
			System.out.println("============="+str+"出列！=============");
		}*/

       /* System.out.println("=============队列中所有消息=============");
        for (String str : JedisUtils.getJedis().lrange("6666", 0, -1)) {
            System.out.println(str);
        };*/

//        JedisUtils.delete(CacheKey.PARK_SLOT_KEY);
//        JedisUtils.rpush(CacheKey.PARK_SLOT_KEY, "", "", "", "", "", "");

        List<String> parkSlots = JedisUtil.lrange(CacheKey.PARK_SLOT_KEY, 0L, -1L);
        /*for (int i = 0; i < parkSlots.size(); i++) {
            String value = parkSlots.get(i);
            ParkSlot parkSlot = JSON.parseObject(value, ParkSlot.class);
            if (parkSlot == null) {
                System.out.println("this slot is free.");
                parkSlot = new ParkSlot();
                parkSlot.setId(i);
                parkSlot.setCarNumber(RandomStringUtils.randomNumeric(8));
                parkSlot.setColor(ColorEnum.BLACK.value);
                JedisUtil.getJedis().lset(CacheKey.PARK_SLOT_KEY, i, JSON.toJSONString(parkSlot));
                continue;
            }
            System.out.println("carNumber: " + parkSlot.getCarNumber());
        }*/


    }
}
