package com.me.oauth.security.custom;


import com.me.common.HZeroService;
import com.me.redis.captcha.CaptchaImageHelper;
import org.springframework.security.authentication.AuthenticationDetailsSource;
import org.springframework.security.web.authentication.WebAuthenticationDetails;

import javax.servlet.http.HttpServletRequest;

/**
 * 自定义获取AuthenticationDetails 用于封装传进来的验证码
 *
 * @author bojiangzhou 2019/02/26
 */
public class CustomAuthenticationDetailsSource implements AuthenticationDetailsSource<HttpServletRequest, WebAuthenticationDetails> {

    private CaptchaImageHelper captchaImageHelper;

    public CustomAuthenticationDetailsSource(CaptchaImageHelper captchaImageHelper) {
        this.captchaImageHelper = captchaImageHelper;
    }

    @Override
    public WebAuthenticationDetails buildDetails(HttpServletRequest request) {
        String cacheCaptcha = captchaImageHelper.getCaptcha(request, HZeroService.Oauth.CODE);
        request.setAttribute(CustomWebAuthenticationDetails.FIELD_CACHE_CAPTCHA, cacheCaptcha);
        return new CustomWebAuthenticationDetails(request);
    }

    protected CaptchaImageHelper getCaptchaImageHelper() {
        return captchaImageHelper;
    }
}
