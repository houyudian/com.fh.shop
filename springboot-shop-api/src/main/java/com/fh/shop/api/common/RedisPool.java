package com.fh.shop.api.common;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class RedisPool {

    private static JedisPool jedisPool;

    private RedisPool() {
    }

    private static void initPool() {
        JedisPoolConfig poolConfig = new JedisPoolConfig();
        poolConfig.setMaxTotal(1000);
        poolConfig.setMinIdle(500);
        poolConfig.setMaxIdle(500);
        poolConfig.setTestOnBorrow(true);
        poolConfig.setTestOnReturn(true);
        jedisPool = new JedisPool(poolConfig, "192.168.24.144", 6379);
    }

    static {
        initPool();
    }

    public static Jedis getResource() {
        return jedisPool.getResource();
    }
}
