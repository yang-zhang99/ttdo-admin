package com.me.gateway.ratelimit.dimension;


import com.me.gateway.ratelimit.switcher.DoubleModeSwitcher;
import com.me.gateway.ratelimit.switcher.ModeSwitcher;
import com.me.gateway.ratelimit.switcher.SwitcherDelegate;
import com.me.utils.KeyGenerator;
import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Optional;

/**
 * @author XCXCXCXCX
 * @date 2019/10/17
 * @project hzero-gateway
 */
public class OriginKeyResolver implements KeyResolver, SwitcherDelegate {

    private static final String PREFIX = "origin";

    private ModeSwitcher modeSwitcher = new DoubleModeSwitcher();

    public OriginKeyResolver() {
        this.modeSwitcher.switchMode(null, null);
    }

    public OriginKeyResolver(String mode, String listString) {
        this.modeSwitcher.switchMode(mode, listString);
    }

    @Override
    public ModeSwitcher getModeSwitcher() {
        return modeSwitcher;
    }

    @Override
    public Mono<String> resolve(ServerWebExchange exchange) {
        return Mono.just(Optional.ofNullable(getOrigin(exchange)).orElse(KeyGenerator.generate()));
    }

    private String getOrigin(ServerWebExchange exchange) {
        String originKey = getModeSwitcher().execute(exchange.getRequest().getHeaders().getOrigin());
        return originKey == null ? null : PREFIX + originKey;
    }

}
