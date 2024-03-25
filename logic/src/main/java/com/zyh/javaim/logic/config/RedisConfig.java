package com.zyh.javaim.logic.config;

import io.netty.channel.ChannelId;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

@Configuration
public class RedisConfig {

    @Bean
    public RedisTemplate<String, ChannelId> redisTemplate(LettuceConnectionFactory connectionFactory) {
        RedisTemplate<String, ChannelId> template = new RedisTemplate<>();
        template.setConnectionFactory(connectionFactory);
        return template;
    }
}