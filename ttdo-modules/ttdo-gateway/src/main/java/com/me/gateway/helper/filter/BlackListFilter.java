package com.me.gateway.helper.filter;

import com.me.gateway.helper.api.HelperFilter;
import com.me.gateway.helper.config.ListFilterProperties;
import com.me.gateway.helper.domain.entity.RequestContext;
import com.me.redis.RedisHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

/**
 * 黑名单
 */
@Component
public class BlackListFilter implements HelperFilter {

    private static final String TRUE = "true";
    private static final String BLACK_LIST_ENABLE_KEY = "gateway.BLACK_LIST_ENABLE";
    private static final String BLACK_LIST_KEY = "gateway.BLACK_LIST";
    private ListFilterProperties filterProperties;
    private RedisHelper redisHelper;


    @Autowired
    public BlackListFilter(ListFilterProperties filterProperties, RedisHelper redisHelper) {
        this.filterProperties = filterProperties;
        this.redisHelper = redisHelper;
    }

    @Override
    public int filterOrder() {
        return 2;
    }

    @Override
    public boolean shouldFilter(RequestContext requestContext) {
        String enableBlackList = this.redisHelper.strGet("gateway.BLACK_LIST_ENABLE");
        if ("true".equalsIgnoreCase(enableBlackList)) {
            return true;
        } else {
            return !StringUtils.hasText(enableBlackList) && this.filterProperties.getBlackList().isEnable();
        }
    }

    @Override
    public boolean run(RequestContext requestContext) {
//        if (!IpUtils.match(CollectionUtils.merge(this.redisHelper.lstAll("gateway.BLACK_LIST"), this.filterProperties.getBlackList().getIp()), ServerRequestUtils.getRealIp(requestContext.getServletRequest()))) {
            return true;
//        } else {
//            requestContext.response.setStatus(CheckState.PERMISSION_BLACK_LIST_FORBIDDEN);
//            return false;
//        }
    }
}
