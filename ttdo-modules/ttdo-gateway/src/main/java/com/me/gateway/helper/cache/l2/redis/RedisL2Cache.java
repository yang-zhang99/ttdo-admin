package com.me.gateway.helper.cache.l2.redis;

import com.me.gateway.helper.cache.l2.L2Cache;
import org.springframework.cache.Cache;

public class RedisL2Cache extends L2Cache {

    public RedisL2Cache(Cache cache) {
        super(cache);
    }

}
