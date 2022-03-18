package com.caomei.common.config;


import org.apache.commons.lang3.SerializationUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.eis.CachingSessionDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import java.io.Serializable;


public class RedisSessionDao extends CachingSessionDAO {

    private static final String PREFIX = "SHIRO_SESSION_ID";

    private static final int EXPRIE = 10000;

    private static final Logger LOGGER = LoggerFactory.getLogger(RedisSessionDao.class);

    private static JedisPool jedisPool;

    static {
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxTotal(200);
        config.setMaxIdle(50);
        config.setMinIdle(8);//设置最小空闲数
        config.setMaxWaitMillis(10000);
        config.setTestOnBorrow(true);
        config.setTestOnReturn(true);
        //Idle时进行连接扫描
        config.setTestWhileIdle(true);
        //表示idle object evitor两次扫描之间要sleep的毫秒数
        config.setTimeBetweenEvictionRunsMillis(30000);
        //表示idle object evitor每次扫描的最多的对象数
        config.setNumTestsPerEvictionRun(10);
        //表示一个对象至少停留在idle状态的最短时间，然后才能被idle object evitor扫描并驱逐；这一项只有在timeBetweenEvictionRunsMillis大于0时才有意义
        config.setMinEvictableIdleTimeMillis(60000);

        jedisPool = new JedisPool(config, "127.0.0.1", 6379, 10000);
    }



    @Override
    protected Serializable doCreate(Session session) {
        LOGGER.info("--------doCreate-----");
        Serializable serializable = this.generateSessionId(session);
        assignSessionId(session, serializable);
        Jedis jedis = jedisPool.getResource();
        session.setTimeout(EXPRIE*1000);
        jedis.setex(getByteKey(serializable),EXPRIE,SerializationUtils.serialize((Serializable)session) );
        JedisUtil.closeJedis(jedis);
        return serializable;
    }


    @Override
    protected Session doReadSession(Serializable serializable) {
        if(serializable ==null){
            return null;
        }

        LOGGER.info("--------doReadSession-----");
        Jedis jedis = jedisPool.getResource();
        Session session = null;
        byte[] s = jedis.get(getByteKey(serializable));
        if (s != null) {
            session = SerializationUtils.deserialize(s);
            jedis.expire((PREFIX+serializable).getBytes(),EXPRIE);
        }
        //判断是否有会话  没有返回NULL
        if(session==null){
            return null;
        }
        JedisUtil.closeJedis(jedis);
        return session;
    }

    private byte[] getByteKey(Object k){
        if(k instanceof String){
            String key = PREFIX+k;
            return key.getBytes();
        }else {
            return SerializationUtils.serialize((Serializable) k);
        }
    }
    @Override
    protected void doUpdate(Session session) {
        LOGGER.info("--------doUpdate-----");
        if(session==null){
            return ;
        }
        //((WebSessionKey)sessionKey)



        Jedis jedis = jedisPool.getResource();
        session.setTimeout(EXPRIE*1000);
       /*jedis.set(getByteKey(session.getId()),SerializationUtils.serialize((Serializable)session));
       jedis.expire(SerializationUtils.serialize((PREFIX+session.getId())),EXPRIE);*/
        jedis.setex(getByteKey(session.getId()),EXPRIE,SerializationUtils.serialize((Serializable)session) );


    }


    @Override
    protected void doDelete(Session session) {
        LOGGER.info("--------doDelete-----");
        Jedis jedis = jedisPool.getResource();
        jedis.del(getByteKey(session.getId()));
        JedisUtil.closeJedis(jedis);

    }


}


