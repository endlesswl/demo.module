package com.lingcaibao.weixin.core.service;

/**
 * Created by jianhe on 14-5-8.
 */

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;


/**
 * Redis 服务实现类
 */
@Service(value = "redisService")
public class RedisServiceImpl implements RedisService {

    private static Logger logger = LoggerFactory.getLogger(RedisServiceImpl.class);

    private static final String CHARSET = "UTF-8";

    @Autowired
    private RedisTemplate<String, Serializable> redisTemplate;

    public void setRedisTemplate(
            RedisTemplate<String, Serializable> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public boolean set(final byte[] key, final byte[] value,
                       final long activeTime) {
        return redisTemplate.execute(new RedisCallback<Boolean>() {
            public Boolean doInRedis(RedisConnection connection)
                    throws DataAccessException {
                boolean rs = true;
                connection.set(key, value);
                if (activeTime > 0) {
                    rs = connection.expire(key, activeTime);
                }
                return rs;
            }
        });
    }

    @Override
    public boolean set(String key, String value, long activeTime) {
        return this.set(key.getBytes(), value.getBytes(), activeTime);
    }

    @Override
    public boolean set(String key, String value) {
        return this.set(key, value, 0L);
    }

    @Override
    public boolean set(byte[] key, byte[] value) {
        return this.set(key, value, 0L);
    }

    @Override
    public String get(final String key) {
        return redisTemplate.execute(new RedisCallback<String>() {
            public String doInRedis(RedisConnection connection)
                    throws DataAccessException {
                try {
                    byte[] value = connection.get(key.getBytes());
                    return value == null ? "" : new String(value, CHARSET);
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                return "";
            }
        });
    }

    @Override
    public Set<String> matchKeys(String pattern) {
        return redisTemplate.keys(pattern);

    }

    @Override
    public boolean exists(final String key) {
        return redisTemplate.execute(new RedisCallback<Boolean>() {
            public Boolean doInRedis(RedisConnection connection)
                    throws DataAccessException {
                return connection.exists(key.getBytes());
            }
        });
    }

    @Override
    public boolean flushDB() {
        return redisTemplate.execute(new RedisCallback<Boolean>() {
            public Boolean doInRedis(RedisConnection connection)
                    throws DataAccessException {
                connection.flushDb();
                return true;
            }
        });
    }

    @Override
    public long delete(final Collection<String> keys) {
        return redisTemplate.execute(new RedisCallback<Long>() {
            public Long doInRedis(RedisConnection connection)
                    throws DataAccessException {
                long result = 0;
                for (String key : keys) {
                    result = connection.del(key.getBytes());
                }
                return result;
            }
        });
    }

    @Override
    public long delete(final String... keys) {
        Collection<String> cols = new ArrayList<String>();
        for (String key : keys) {
            cols.add(key);
        }
        return this.delete(cols);
    }

    @Override
    public Long incr(final String key) {
        return redisTemplate.execute(new RedisCallback<Long>() {
            public Long doInRedis(RedisConnection connection)
                    throws DataAccessException {
                return connection.incr(key.getBytes());
            }
        });
    }

    @Override
    public Long decr(final String key) {
        return redisTemplate.execute(new RedisCallback<Long>() {
            public Long doInRedis(RedisConnection connection)
                    throws DataAccessException {
                return connection.decr(key.getBytes());
            }
        });
    }

    @Override
    public boolean setHash(final String name, final String key, final String value) {
        return redisTemplate.execute(new RedisCallback<Boolean>() {
            public Boolean doInRedis(RedisConnection connection)
                    throws DataAccessException {
                return connection.hSet(name.getBytes(), key.getBytes(), value.getBytes());
            }
        });
    }

    @Override
    public String getHash(final String name, final String key) {
        return redisTemplate.execute(new RedisCallback<String>() {
            public String doInRedis(RedisConnection connection)
                    throws DataAccessException {
                try {
                    byte[] value = connection.hGet(name.getBytes(), key.getBytes());
                    return value == null ? "" : new String(value, CHARSET);
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                    return "";
                }
            }
        });
    }

    @Override
    public Long hashLen(final String name) {
        return redisTemplate.execute(new RedisCallback<Long>() {
            public Long doInRedis(RedisConnection connection)
                    throws DataAccessException {
                return connection.hLen(name.getBytes());
            }
        });
    }

    @Override
    public Set<byte[]> getHashKeys(final String name) {
        return redisTemplate.execute(new RedisCallback<Set<byte[]>>() {
            public Set<byte[]> doInRedis(RedisConnection connection)
                    throws DataAccessException {
                return connection.hKeys(name.getBytes());
            }
        });
    }
}

