package com.caomei.music.config;

import org.apache.commons.lang3.SerializationUtils;
import org.apache.shiro.cache.Cache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.io.Serializable;
import java.util.*;

public class JedisCache<K,V> implements Cache<K, V> ,Serializable{
    private static final Logger LOGGER = LoggerFactory.getLogger(JedisCache.class);

    private static final String PREFIX = "SHIRO_SESSION_ID";


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

    private byte[] getByteKey(K k){
        if(k instanceof String){
            String key = PREFIX+k;
            return key.getBytes();
        }else {
            return SerializationUtils.serialize((Serializable) k);
        }
    }

    @Override
    public int size() {
        Jedis jedis = jedisPool.getResource();
        Long size = jedis.dbSize();
        return size.intValue();
    }

    @Override
    public Set<K> keys() {
        Jedis jedis = jedisPool.getResource();

        Set<byte[]> bytes = jedis.keys( (PREFIX + new String("*")).getBytes());
        Set<K>  keys = new HashSet<>();
        if(bytes!=null){
            for (byte[] b: bytes) {
                keys.add((K) SerializationUtils.deserialize(b));
            }
        }
        JedisUtil.closeJedis(jedis);
        return  keys;
    }

    @Override
    public Collection<V> values() {
        Set<K> keys = this.keys();
        Jedis jedis = jedisPool.getResource();
        List<V> lists = new ArrayList<>();
        for (K k:keys) {
            byte[] bytes = jedis.get(getByteKey(k));
            lists.add((V) SerializationUtils.deserialize(bytes));
        }
        JedisUtil.closeJedis(jedis);
        return lists;
    }

    @Override
    public void clear() {
        jedisPool.getResource().flushDB();
    }

    @Override
    public V put(K k, V v) {
        LOGGER.info("key---->"+k+"value---->"+v);
        Jedis jedis = jedisPool.getResource();
        jedis.set(getByteKey(k), SerializationUtils.serialize((Serializable) v));
        jedis.expire(getByteKey(k),10000);
        byte[] bytes = jedis.get(SerializationUtils.serialize(getByteKey(k)));
        JedisUtil.closeJedis(jedis);
        if(bytes==null){
            return null;
        }
        return SerializationUtils.deserialize(bytes);
    }

    @Override
    public V get(K k) {
        LOGGER.info("get------>key="+k);
        if(k==null){
            return null;
        }
        //System.out.println(k);
        Jedis jedis = jedisPool.getResource();
        byte[] bytes = jedis.get(getByteKey(k));
        JedisUtil.closeJedis(jedis);
        if(bytes==null){
            return null;
        }
        return SerializationUtils.deserialize(bytes);
    }

    @Override
    public V remove(K k) {
        Jedis jedis = jedisPool.getResource();
        byte[] bytes = jedis.get(getByteKey(k));
        jedis.del(getByteKey(k));
        JedisUtil.closeJedis(jedis);
        if(bytes==null){
            return null;
        }
        return SerializationUtils.deserialize(bytes);
    }




}


