package com.me.gateway.ratelimit;

import com.me.gateway.ratelimit.dimension.*;
import com.me.gateway.ratelimit.limiter.EnhancedRedisRateLimiter;
import com.me.gateway.ratelimit.switcher.DoubleModeSwitcher;
import com.me.gateway.ratelimit.switcher.ModeSwitcher;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.gateway.filter.ratelimit.RedisRateLimiter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.validation.Validator;

import java.util.List;
// todo
@Configuration
public class RateLimitConfiguration {

//    @Bean
//    public ModeSwitcher modeSwitcher() {
//        return new DoubleModeSwitcher();
//    }
//
//    @Bean
//    public OriginKeyResolver originKeyResolver() {
//        return new OriginKeyResolver();
//    }
//
//    @Bean
//    public TenantKeyResolver tenantKeyResolver() {
//        return new TenantKeyResolver();
//    }
//
//    @Bean
//    public UrlKeyResolver urlKeyResolver() {
//        return new UrlKeyResolver();
//    }
//
//    @Bean
//    public UserKeyResolver userKeyResolver() {
//        return new UserKeyResolver();
//    }
//
//    @Bean
//    public RoleKeyResolver roleKeyResolver() {
//        return new RoleKeyResolver();
//    }
//
//    @Primary
//    @Bean
//    public EnhancedRedisRateLimiter enhancedRedisRateLimiter(ReactiveRedisTemplate<String, String> redisTemplate,
//                                                             @Qualifier(RedisRateLimiter.REDIS_SCRIPT_NAME) RedisScript<List<Long>> redisScript,
//                                                             Validator validator) {
//        return new EnhancedRedisRateLimiter(redisTemplate, redisScript, validator);
//    }

}
