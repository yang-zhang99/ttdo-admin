package com.me.gateway.helper.service;


import com.me.gateway.helper.domain.entity.CheckResponse;
import com.me.gateway.helper.domain.entity.RequestContext;

/**
 * 缺失权限应用服务实现
 *
 * @author KAIBING.JIANG@HAND-CHINA.COM
 */
public interface PermissionCheckService {
    /**
     * 保存缺失权限记录
     * @param requestContext 请求
     * @param checkResponse 检查请求
     */
    void savePermissionCheck(RequestContext requestContext, CheckResponse checkResponse);
}
