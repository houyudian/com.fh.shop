package com.fh.shop.api.common;

import redis.clients.jedis.Jedis;

public class RedisUtil {

    public static String setNxEx(String key, String value, int seconds) {
        Jedis resource = null;
        try {
            resource = RedisPool.getResource();
            String result = resource.set(key, value, "NX", "EX", seconds);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } finally {
            if (null != resource) {
                resource.close();
            }
        }

    }

    public static String get(String key) {
        Jedis jedis = null;
        String result;
        try {
            jedis = RedisPool.getResource();
            result = jedis.get(key);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } finally {
            if (null != jedis) {
                jedis.close();
            }
        }

        return result;
    }

    public static void setEx(String key, String value, int seconds) {
        Jedis jedis = null;
        try {
            jedis = RedisPool.getResource();
            jedis.setex(key, seconds, value);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } finally {
            if (null != jedis) {
                jedis.close();
            }
        }
    }

    public static Long delete(String key) {
        Jedis jedis = null;
        try {
            jedis = RedisPool.getResource();
           return jedis.del(key);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } finally {
            if (null != null) {
                jedis.close();
            }
        }
    }

    public static void set(String key, String value) {
        Jedis jedis = null;
        try {
            jedis = RedisPool.getResource();
            jedis.set(key, value);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } finally {
            if (null != jedis) {
                jedis.close();
            }
        }
    }

    public static boolean exist(String key) {
        Jedis resource = null;
        try {
            resource = RedisPool.getResource();
            return resource.exists(key);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } finally {
            if (resource != null) {
                resource.close();
            }
        }
    }

    public static void expire(String key, int seconds) {
        Jedis resource = null;
        try {
            resource = RedisPool.getResource();
            resource.expire(key, seconds);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } finally {
            if (resource != null) {
                resource.close();
            }
        }

    }
}
