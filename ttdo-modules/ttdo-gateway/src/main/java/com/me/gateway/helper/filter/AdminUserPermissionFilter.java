package com.me.gateway.helper.filter;

import com.me.gateway.helper.api.HelperFilter;
import com.me.gateway.helper.domain.entity.CheckState;
import com.me.gateway.helper.domain.entity.RequestContext;
import org.springframework.stereotype.Component;

/**
 * 管理用户权限
 */
@Component
public class AdminUserPermissionFilter implements HelperFilter {
    @Override
    public int filterOrder() {
        return 70;
    }

    @Override
    public boolean shouldFilter(RequestContext context) {
        return context.getCustomUserDetails() != null && context.getCustomUserDetails().getAdmin() != null && context.getCustomUserDetails().getAdmin();
    }

    @Override
    public boolean run(RequestContext context) {
        context.response.setStatus(CheckState.SUCCESS_ADMIN);
        context.response.setMessage("Admin user have access to the interface, username: " + context.getCustomUserDetails().getUsername());
        return false;
    }
}
