package com.me.gateway.helper.filter;

import com.me.gateway.helper.api.HelperFilter;
import com.me.gateway.helper.domain.vo.CustomUserDetailsWithResult;
import com.me.gateway.helper.domain.entity.CheckState;
import com.me.gateway.helper.domain.entity.RequestContext;
import com.me.gateway.helper.service.GetUserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class GetUserDetailsFilter implements HelperFilter {
    private GetUserDetailsService getUserDetailsService;

    public GetUserDetailsFilter(GetUserDetailsService getUserDetailsService) {
        this.getUserDetailsService = getUserDetailsService;
    }

    public int filterOrder() {
        return 40;
    }

    public boolean shouldFilter(RequestContext context) {
        return true;
    }

    public boolean run(RequestContext context) {
        String accessToken = context.request.accessToken;
        if (StringUtils.isEmpty(accessToken)) {
            context.response.setStatus(CheckState.PERMISSION_ACCESS_TOKEN_NULL);
            context.response.setMessage("Access_token is empty, Please login and set access_token by HTTP header 'Authorization'");
            return false;
        } else {
            CustomUserDetailsWithResult result = this.getUserDetailsService.getUserDetails(accessToken);
            if (result.getCustomUserDetails() == null) {
                context.response.setStatus(result.getState());
                context.response.setMessage(result.getMessage());
                return false;
            } else {
                context.setCustomUserDetails(result.getCustomUserDetails());
                return true;
            }
        }
    }
}
