package com.me.redis;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;

import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * 动态库 Redis 帮助类
 */
public class DynamicRedisHelper extends RedisHelper {

    private final Logger logger = LoggerFactory.getLogger(DynamicRedisHelper.class);
    private final DynamicRedisTemplate<String, String> redisTemplate;

    public static final long DEFAULT_EXPIRE = 86400L;
    public static final long NOT_EXPIRE = -1L;

    public DynamicRedisHelper(DynamicRedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public RedisTemplate<String, String> getRedisTemplate() {
        return this.redisTemplate;
    }

    /**
     * 设置当前的 Redis 库
     * @param database database
     */
    public void setCurrentDatabase(int database) {
        RedisDatabaseThreadLocal.set(database);
    }

    /**
     * 设置当前设置的 Redis 库
     */
    public void clearCurrentDatabase() {
        RedisDatabaseThreadLocal.clear();
    }

    public void delKey(String key) {
        this.redisTemplate.delete(key);
    }

    public Boolean hasKey(String key) {
        return this.redisTemplate.hasKey(key);
    }

    public Long getExpire(String key) {
        return this.redisTemplate.getExpire(key);
    }

    public Long getExpire(String key, final TimeUnit timeUnit) {
        return this.redisTemplate.getExpire(key, timeUnit);
    }

    public Boolean setExpire(String key) {
        return this.setExpire(key, 86400L, TimeUnit.SECONDS);
    }

    public Boolean setExpire(String key, long expire) {
        return this.setExpire(key, expire, TimeUnit.SECONDS);
    }

    public Boolean setExpire(String key, long expire, TimeUnit timeUnit) {
        return this.redisTemplate.expire(key, expire, timeUnit == null ? TimeUnit.SECONDS : timeUnit);
    }

    public void delKeys(Collection<String> keys) {
        Set<String> hs = new HashSet(keys);
        this.redisTemplate.delete(hs);
    }

    private void deleteFullKey(String fullKey) {
        this.redisTemplate.delete(fullKey);
    }

    private void deleteFullKeys(Collection<String> fullKeys) {
        this.redisTemplate.delete(fullKeys);
    }

    public void strSet(String key, String value, long expire, TimeUnit timeUnit) {
        this.redisTemplate.opsForValue().set(key, value);
        if (expire != -1L) {
            this.setExpire(key, expire, timeUnit == null ? TimeUnit.SECONDS : timeUnit);
        }

    }

    public void strSet(String key, String value) {
        this.redisTemplate.opsForValue().set(key, value);
    }

    public String strGet(String key) {
        return (String) this.redisTemplate.opsForValue().get(key);
    }

    public String strGet(String key, long expire, TimeUnit timeUnit) {
        String value =   this.redisTemplate.opsForValue().get(key);
        if (expire != -1L) {
            this.setExpire(key, expire, timeUnit == null ? TimeUnit.SECONDS : timeUnit);
        }

        return value;
    }

    public <T> T strGet(String key, Class<T> clazz) {
        String value =  this.redisTemplate.opsForValue().get(key);
        return value == null ? null : this.fromJson(value, clazz);
    }

    public <T> T strGet(String key, Class<T> clazz, long expire, TimeUnit timeUnit) {
        String value =   this.redisTemplate.opsForValue().get(key);
        if (expire != -1L) {
            this.setExpire(key, expire, timeUnit == null ? TimeUnit.SECONDS : timeUnit);
        }

        return value == null ? null : this.fromJson(value, clazz);
    }

    public String strGet(String key, Long start, Long end) {
        return this.redisTemplate.opsForValue().get(key, start, end);
    }

    public Long strIncrement(String key, Long delta) {
        return this.redisTemplate.opsForValue().increment(key, delta);
    }

    public Long lstLeftPush(String key, String value) {
        return this.redisTemplate.opsForList().leftPush(key, value);
    }

    public Long lstLeftPushAll(String key, Collection<String> values) {
        return this.redisTemplate.opsForList().leftPushAll(key, values);
    }

    public Long lstRightPush(String key, String value) {
        return this.redisTemplate.opsForList().rightPush(key, value);
    }

    public Long lstRightPushAll(String key, Collection<String> values) {
        return this.redisTemplate.opsForList().rightPushAll(key, values);
    }

    public List<String> lstRange(String key, long start, long end) {
        return this.redisTemplate.opsForList().range(key, start, end);
    }

    public List<String> lstAll(String key) {
        return this.lstRange(key, 0L, this.lstLen(key));
    }

    public String lstLeftPop(String key) {
        return this.redisTemplate.opsForList().leftPop(key);
    }

    public String lstRightPop(String key) {
        return this.redisTemplate.opsForList().rightPop(key);
    }

    public String lstLeftPop(String key, long timeout, TimeUnit timeUnit) {
        return  this.redisTemplate.opsForList().leftPop(key, timeout, timeUnit);
    }

    public String lstRightPop(String key, long timeout, TimeUnit timeUnit) {
        return  this.redisTemplate.opsForList().rightPop(key, timeout, timeUnit);
    }

    public Long lstLen(String key) {
        return this.redisTemplate.opsForList().size(key);
    }

    public void lstSet(String key, long index, String value) {
        this.redisTemplate.opsForList().set(key, index, value);
    }

    public Long lstRemove(String key, long index, String value) {
        return this.redisTemplate.opsForList().remove(key, index, value);
    }

    public Object lstIndex(String key, long index) {
        return this.redisTemplate.opsForList().index(key, index);
    }

    public void lstTrim(String key, long start, long end) {
        this.redisTemplate.opsForList().trim(key, start, end);
    }

    public Long setAdd(String key, String[] values) {
        return this.redisTemplate.opsForSet().add(key, values);
    }

    public Long setIrt(String key, String... values) {
        return this.redisTemplate.opsForSet().add(key, values);
    }

    public Set<String> setMembers(String key) {
        return this.redisTemplate.opsForSet().members(key);
    }

    public Boolean setIsmember(String key, String o) {
        return this.redisTemplate.opsForSet().isMember(key, o);
    }

    public Long setSize(String key) {
        return this.redisTemplate.opsForSet().size(key);
    }

    public Set<String> setIntersect(String key, String otherKey) {
        return this.redisTemplate.opsForSet().intersect(key, otherKey);
    }

    public Set<String> setUnion(String key, String otherKey) {
        return this.redisTemplate.opsForSet().union(key, otherKey);
    }

    public Set<String> setUnion(String key, Collection<String> otherKeys) {
        return this.redisTemplate.opsForSet().union(key, otherKeys);
    }

    public Set<String> setDifference(String key, String otherKey) {
        return this.redisTemplate.opsForSet().difference(key, otherKey);
    }

    public Set<String> setDifference(String key, Collection<String> otherKeys) {
        return this.redisTemplate.opsForSet().difference(key, otherKeys);
    }

    public Long setDel(String key, String value) {
        return this.redisTemplate.opsForSet().remove(key, new Object[]{value});
    }

    public Long setRemove(String key, Object[] value) {
        return this.redisTemplate.opsForSet().remove(key, value);
    }

    /**
     * ZSet Zadd 命令用于将一个或多个成员元素及其分数值加入到有序集当中。 如果某个成员已经是有序集的成员，
     * 那么更新这个成员的分数值，并通过重新插入这个成员元素，来保证该成员在正确的位置上。分数值可以是整数值
     * 或双精度浮点数。 如果有序集合 key 不存在，则创建一个空的有序集并执行 ZADD 操作。 当 key 存在但不是
     * 有序集类型时，返回一个错误。
     *
     * @param key
     * @param value
     * @param score
     */
    public Boolean zSetAdd(String key, String value, double score) {
        return this.redisTemplate.opsForZSet().add(key, value, score);
    }

    /**
     * ZSet 返回有序集合中，指定元素的分值
     *
     * @param key key
     * @param value value
     * @return Double
     */
    public Double zSetScore(String key, String value) {
        return this.redisTemplate.opsForZSet().score(key, value);
    }

    public Double zSetIncrementScore(String key, String value, double delta) {
        return this.redisTemplate.opsForZSet().incrementScore(key, value, delta);
    }

    /**
     * ZSet 返回指定元素在有序集合中的排名，其中排名按照元素的分值从小到大计算。排名以 0 开始
     *
     * @param key key
     * @param value value
     * @return Long Long
     */
    public Long zSetRank(String key, String value) {
        return this.redisTemplate.opsForZSet().rank(key, value);
    }

    public Long zSetReverseRank(String key, String value) {
        return this.redisTemplate.opsForZSet().reverseRank(key, value);
    }

    public Long zSetSize(String key) {
        return this.redisTemplate.opsForZSet().size(key);
    }

    public Long zSetRemove(String key, String value) {
        return this.redisTemplate.opsForZSet().remove(key, new Object[]{value});
    }

    public Set<String> zSetRange(String key, Long start, Long end) {
        return this.redisTemplate.opsForZSet().range(key, start, end);
    }

    public Set<String> zSetReverseRange(String key, Long start, Long end) {
        return this.redisTemplate.opsForZSet().reverseRange(key, start, end);
    }

    public Set<String> zSetRangeByScore(String key, Double min, Double max) {
        return this.redisTemplate.opsForZSet().rangeByScore(key, min, max);
    }

    public Set<String> zSetReverseRangeByScore(String key, Double min, Double max) {
        return this.redisTemplate.opsForZSet().reverseRangeByScore(key, min, max);
    }

    public Set<String> zSetRangeByScore(String key, Double min, Double max, Long offset, Long count) {
        return this.redisTemplate.opsForZSet().rangeByScore(key, min, max, offset, count);
    }

    public Set<String> zSetReverseRangeByScore(String key, Double min, Double max, Long offset, Long count) {
        return this.redisTemplate.opsForZSet().reverseRangeByScore(key, min, max, offset, count);
    }

    /**
     * ZSet 返回有序集合在升序排列元素的情况下，分值在 min和 max范围内的元素数量
     *
     * @param key key
     * @param min min
     * @param max max
     * @return Long
     */
    public Long zSetCount(String key, Double min, Double max) {
        return this.redisTemplate.opsForZSet().count(key, min, max);
    }

    public void hshPut(String key, String hashKey, String value) {
        this.redisTemplate.opsForHash().put(key, hashKey, value);
    }

    public void hshPutAll(String key, Map<String, String> map) {
        this.redisTemplate.opsForHash().putAll(key, map);
    }

    public byte[] hshGetSerial(String key, String hashKey) {
        RedisSerializer<String> redisSerializer = this.redisTemplate.getStringSerializer();
        return (byte[]) this.redisTemplate.execute((RedisCallback<Object>) (connection) -> {
            try {
                return connection.hGet(redisSerializer.serialize(key), redisSerializer.serialize(hashKey));
            } catch (Exception var6) {
                this.logger.error("获取HASH对象序列失败", var6);
                return null;
            }
        });
    }

    public Boolean hshPutSerial(String key, String hashKey, byte[] value) {
        RedisSerializer<String> redisSerializer = this.redisTemplate.getStringSerializer();
        return (Boolean) this.redisTemplate.execute((RedisCallback<Object>) (connection) -> {
            try {
                return connection.hSet(redisSerializer.serialize(key), redisSerializer.serialize(hashKey), value);
            } catch (Exception var7) {
                this.logger.error("插入HASH对象序列失败", var7);
                return Boolean.FALSE;
            }
        });
    }

    public String hshGet(String key, String hashKey) {
        return (String) this.redisTemplate.opsForHash().get(key, hashKey);
    }

    public List<String> hshMultiGet(String key, Collection<String> hashKeys) {
        Collection<Object> list = new ArrayList<>(hashKeys);
        List<Object> ret = this.redisTemplate.opsForHash().multiGet(key, list);
        return  ret.stream().map((o) -> (String) o).collect(Collectors.toList());
    }

    public Map<String, String> hshGetAll(String key) {
        Map<Object, Object> map = this.redisTemplate.opsForHash().entries(key);
        Map<String, String> ret = new LinkedHashMap<>();
        map.forEach((k, v) -> {
            String var10000 = ret.put((String) k, (String) v);
        });
        return ret;
    }

    public Boolean hshHasKey(String key, String hashKey) {
        return this.redisTemplate.opsForHash().hasKey(key, hashKey);
    }

    public Set<String> hshKeys(String key) {
        Set<Object> set = this.redisTemplate.opsForHash().keys(key);
        return  set.stream().map((o) -> (String) o).collect(Collectors.toSet());
    }

    public List hshVals(String key) {
        List<Object> list = this.redisTemplate.opsForHash().values(key);
        return list.stream().map((o) -> (String) o).collect(Collectors.toList());
    }

    public List<String> hshVals(String key, Collection<String> hashKeys) {
        Collection<Object> list = new ArrayList(hashKeys);
        List<Object> ret = this.redisTemplate.opsForHash().multiGet(key, list);
        return ret.stream().map((o) -> (String) o).collect(Collectors.toList());
    }

    public Long hshSize(String key) {
        return this.redisTemplate.opsForHash().size(key);
    }

    public void hshDelete(String key, Object... hashKeys) {
        this.redisTemplate.opsForHash().delete(key, hashKeys);
    }

    public void hshRemove(String key, Object[] hashKeys) {
        this.redisTemplate.opsForHash().delete(key, hashKeys);
    }

    public <T> String toJson(T object) {
        if (object == null) {
            return "";
        } else if (!(object instanceof Integer) && !(object instanceof Long) && !(object instanceof Float) && !(object instanceof Double) && !(object instanceof Boolean) && !(object instanceof String)) {
            try {
                return objectMapper.writeValueAsString(object);
            } catch (JsonProcessingException var3) {
                return "";
            }
        } else {
            return String.valueOf(object);
        }
    }

    public <T> T fromJson(String json, Class<T> clazz) {
        if (!StringUtils.isBlank(json) && clazz != null) {
            try {
                return objectMapper.readValue(json, clazz);
            } catch (Exception var4) {
                if (this.logger.isErrorEnabled()) {
                    this.logger.error(var4.getMessage(), var4);
                }

                return null;
            }
        } else {
            return null;
        }
    }

    public <T> void objectSet(String key, T object) {
        this.strSet(key, this.toJson(object));
    }

    public int deleteKeysWithPrefix(String keyPrefix) {
        Set<String> keys = this.keys(keyPrefix + '*');
        if (keys != null && !keys.isEmpty()) {
            this.deleteFullKeys(keys);
            return keys.size();
        } else {
            return 0;
        }
    }

    public Set<String> keys(String pattern) {
        return this.redisTemplate.keys(pattern);
    }

    public Boolean strSetIfAbsent(String key, String value) {
        return this.redisTemplate.opsForValue().setIfAbsent(key, value);
    }

    public Long zSetRemoveByScore(String key, double min, double max) {
        return this.redisTemplate.opsForZSet().removeRangeByScore(key, min, max);
    }
}
