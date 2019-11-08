package com.zynn.common.core.config.redis;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.*;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.GenericToStringSerializer;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.io.Serializable;

/**
 * redis配置
 * @author 涂炜
 * @date 2018年6月15日15:29:56
 */
@Configuration
@EnableAutoConfiguration
public class RedisConfig {

//    @Bean
//    @ConfigurationProperties(prefix = "spring.redis.pool")
//    public JedisPoolConfig getRedisConfig() {
//        JedisPoolConfig config = new JedisPoolConfig();
//        return config;
//    }
//
//    @Bean
//    @ConfigurationProperties(prefix = "spring.redis")
//    public JedisConnectionFactory getConnectionFactory() {
//        JedisConnectionFactory factory = new JedisConnectionFactory();
//        factory.setUsePool(true);
//        JedisPoolConfig config = getRedisConfig();
//        factory.setPoolConfig(config);
//        return factory;
//    }



    @Bean
    @Primary
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory connectionFactory) {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        //使用Jackson2JsonRedisSerializer来序列化和反序列化redis的value值
//        Jackson2JsonRedisSerializer serializer = new Jackson2JsonRedisSerializer(Object.class);
        ObjectMapper mapper = new ObjectMapper();
        mapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        mapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
//        serializer.setObjectMapper(mapper);

        template.setValueSerializer(new GenericToStringSerializer(Object.class));
        //使用StringRedisSerializer来序列化和反序列化redis的key值
        template.setKeySerializer(new StringRedisSerializer());
        template.setHashKeySerializer(new StringRedisSerializer());
//        template.setHashValueSerializer(serializer);
        template.setConnectionFactory(connectionFactory);
        ValueOperations<String, Object> stringObjectValueOperations = template.opsForValue();
        return template;
    }




    @Bean
    @Primary
    public ValueOperations getValueOperations(RedisTemplate<String, Object> redisTemplate){
        return  redisTemplate.opsForValue();
    }

    @Bean
    @Primary
    public ListOperations getListOperations(RedisTemplate<String, Object> redisTemplate){
        return  redisTemplate.opsForList();
    }


    @Bean
    @Primary
    public HashOperations getHashOperations(RedisTemplate<String, Object> redisTemplate){
        return  redisTemplate.opsForHash();
    }

    @Bean
    @Primary
    public SetOperations getSetOperations(RedisTemplate<String, Object> redisTemplate){
        return  redisTemplate.opsForSet();
    }

    @Bean
    @Primary
    public ZSetOperations getZSetOperations(RedisTemplate<String, Object> redisTemplate){
        return  redisTemplate.opsForZSet();
    }




}