package com.zynn.common.core.config.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.data.redis.support.atomic.RedisAtomicLong;

import java.sql.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * redis服务
 * @author 涂炜
 * @date 2018年6月15日15:30:28
 */
@Configuration
public class RedisService {

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired(required = false)
    public void setRedisTemplate(RedisTemplate redisTemplate) {
        RedisSerializer stringSerializer = new StringRedisSerializer();
        JdkSerializationRedisSerializer jdkSerializationRedisSerializer = new JdkSerializationRedisSerializer();
        redisTemplate.setKeySerializer(stringSerializer);
        redisTemplate.setValueSerializer(jdkSerializationRedisSerializer);
        redisTemplate.setHashKeySerializer(stringSerializer);
        redisTemplate.setHashValueSerializer(jdkSerializationRedisSerializer);
        this.redisTemplate = redisTemplate;
    }

    private String getRealKey(RedisKey key){
        return key.getPrefix() + "_" + key.getKey();
    }

    /**
     * 删除redis数据
     * @param key 键
     */
    public void delete(RedisKey key) {
        redisTemplate.delete(getRealKey(key));
    }

    /**
     * 写入redis string类型的数据
     * @param key 键
     * @param value redis值
     */
    public void stringSetString(RedisKey key, Object value) {
        redisTemplate.opsForValue().set(getRealKey(key), value);
    }

    /**
     * 写入redis string类型的数据
     * @param key 键
     * @param value redis值
     * @param timeout 过期时间
     * @param timeUnit 过期时间单位
     */
    public void stringSetString(RedisKey key, Object value, long timeout, TimeUnit timeUnit) {
        redisTemplate.opsForValue().set(getRealKey(key), value, timeout, timeUnit);
    }

    /**
     * 获取redis string类型的值
     * @param key 键
     * @return redis值
     */
    public Object stringGetString(RedisKey key) {
        return redisTemplate.opsForValue().get(getRealKey(key));
    }

    /**
     * 是否有redis hash类型的数据
     * @param key 键
     * @param hashKey hashKey
     * @return true有,false没有
     */
    public Boolean hashCheckExist(RedisKey key, String hashKey) {
        return redisTemplate.opsForHash().hasKey(getRealKey(key), hashKey);
    }

    /**
     * 获得redis hash类型的值
     * @param key 键
     * @param hashKey hashKey
     * @return redis值
     */
    public Object hashGetValue(RedisKey key, String hashKey) {
        return redisTemplate.opsForHash().get(getRealKey(key), hashKey);
    }

    /**
     * 获得redis hash类型的Map
     * @param key 键
     * @return 返回的Map
     */
    public Map<String, Object> hashGetEntry(RedisKey key) {
        return redisTemplate.opsForHash().entries(getRealKey(key));
    }

    /**
     * 写入redis hash类型的数据
     * @param key 键
     * @param hashKey hashKey
     * @param value redis值
     */
    public void hashPutValue(RedisKey key, String hashKey, Object value) {
        redisTemplate.opsForHash().put(getRealKey(key), hashKey, value);
    }

    /**
     * 获得redis hash类型的hashKey集合
     * @param key 键
     * @return hashKey集合
     */
    public Set<String> hashGetAllHashKey(RedisKey key) {
        return redisTemplate.opsForHash().keys(getRealKey(key));
    }

    /**
     * 获得redis hash类型的value集合
     * @param key 键
     * @return value集合
     */
    public List hashGetAllHashValue(RedisKey key) {
        return redisTemplate.opsForHash().values(getRealKey(key));
    }

    /**
     * 获得redis hash类型的size
     * @param key 键
     * @return size大小
     */
    public Long hashGetHashSize(RedisKey key) {
        return redisTemplate.opsForHash().size(getRealKey(key));
    }

    /**
     * 删除redis hash类型的数据
     * @param key 键
     * @param hashKey hashKey
     * @return 1删除成功,0没有成功
     */
    public Long hashDeleteHashKey(RedisKey key, String hashKey) {
        return redisTemplate.opsForHash().delete(getRealKey(key), hashKey);
    }

    /**
     * 写入redis list类型的数据(从左侧写入)
     * @param key 键
     * @param value 值
     */
    public void listLeftPushList(RedisKey key, Object value) {
        redisTemplate.opsForList().leftPush(getRealKey(key), value);
    }

    /**
     * 获取redis list类型的数据(从左侧获取)
     * @param key 键
     * @return redis值
     */
    public Object listLeftPopList(RedisKey key) {
        return redisTemplate.opsForList().leftPop(getRealKey(key));
    }

    /**
     * 写入redis list类型的数据(从右侧写入)
     * @param key 键
     * @param value 值
     */
    public void listRightPushList(RedisKey key, Object value) {
        redisTemplate.opsForList().rightPush(getRealKey(key), value);
    }

    /**
     * 获取redis list类型的数据(从右侧获取)
     * @param key 键
     * @return redis值
     */
    public Object listRightPopList(RedisKey key) {
        return redisTemplate.opsForList().rightPop(getRealKey(key));
    }

    /**
     * 获得redis list类型的size
     * @param key 键
     * @return size大小
     */
    public Long listSize(RedisKey key) {
        return redisTemplate.opsForList().size(getRealKey(key));
    }

    /**
     * 获得redis list类型的部分值
     * @param key 键
     * @param startIndex 起始index
     * @param endIndex 结束index
     * @return value集合
     */
    public List listRange(RedisKey key, long startIndex, long endIndex) {
        return redisTemplate.opsForList().range(getRealKey(key), startIndex, endIndex);
    }

    /**
     * 获得redis list类型的所有值
     * @param key 键
     * @return redis值
     */
    public List listRangeAll(RedisKey key) {
        return redisTemplate.opsForList().range(getRealKey(key), 0, listSize(key));
    }

    /**
     * 写入redis set类型的数据
     * @param key 键
     * @param value 值
     * @return 1写入成功,0写入失败(重复数据写入失败)
     */
    public Long setAddValue(RedisKey key, Object value) {
        return redisTemplate.opsForSet().add(getRealKey(key), value);
    }

    /**
     * 获得redis set类型的value集合
     * @param key 键
     * @return value集合
     */
    public Set setGetValue(RedisKey key) {
        return redisTemplate.opsForSet().members(getRealKey(key));
    }

    /**
     * 移除redis set类型的数据
     * @param key 键
     * @param value 需要移除的value
     * @return 1删除成功,0删除失败
     */
    public Long setRemoveValue(RedisKey key, Object value) {
        return redisTemplate.opsForSet().remove(getRealKey(key), value);
    }

    /**
     * 写入redis zset类型的数据
     * @param key 键
     * @param value 值
     * @param score 分数
     * @return 是否添加成功
     */
    public Boolean zsetAddValue(RedisKey key, Object value, double score) {
        return redisTemplate.opsForZSet().add(getRealKey(key), value, score);
    }

    /**
     * 获得redis zset类型的在索引区间value集合(按照分数升序)
     * @param key 键
     * @param startIndex 起始index
     * @param endIndex 结束index
     * @return value集合
     */
    public Set zsetSubSetAsc(RedisKey key, long startIndex, long endIndex) {
        return redisTemplate.opsForZSet().range(getRealKey(key), startIndex, endIndex);
    }

    /**
     * 获得redis zset类型的在索引区间value集合(按照分数降序)
     * @param key 键
     * @param startIndex 起始index
     * @param endIndex 结束index
     * @return value集合
     */
    public Set zsetSubSetDesc(RedisKey key, long startIndex, long endIndex) {
        return redisTemplate.opsForZSet().reverseRange(getRealKey(key), startIndex, endIndex);
    }

    /**
     * 获得redis zset类型的在分数区间value集合(按照分数升序)
     * @param key 键
     * @param minScore 最小分数
     * @param maxScore 最大分数
     * @return value集合
     */
    public Set zsetSubSetScoreAsc(RedisKey key, double minScore, double maxScore) {
        return redisTemplate.opsForZSet().rangeByScore(getRealKey(key), minScore, maxScore);
    }

    /**
     * 获得redis zset类型的在分数区间value集合(按照分数降序)
     * @param key 键
     * @param minScore 最小分数
     * @param maxScore 最大分数
     * @return value集合
     */
    public Set zsetSubSetScoreDesc(RedisKey key, double minScore, double maxScore) {
        return redisTemplate.opsForZSet().reverseRangeByScore(getRealKey(key), minScore, maxScore);
    }


    /**
     * 获取Redis自增主键
     * @param key redisKey
     * @return
     */
    public Long generateId(String key) {
        RedisAtomicLong counter = new RedisAtomicLong(key, redisTemplate.getConnectionFactory());
        return counter.incrementAndGet();
    }

    /**
     * 获取Redis自增主键
     * @param key redisKey
     * @return
     */
    public Long resetGenerateId(String key) {
        RedisAtomicLong counter = new RedisAtomicLong(key, redisTemplate.getConnectionFactory());

        return counter.addAndGet(0-counter.incrementAndGet());
    }

    /**
     * 获取Redis自增主键
     * @param key redisKey
     * @return
     */
    public Long getGenerateId(String key) {

        RedisAtomicLong counter = new RedisAtomicLong(key, redisTemplate.getConnectionFactory());

        return counter.get();
    }

}