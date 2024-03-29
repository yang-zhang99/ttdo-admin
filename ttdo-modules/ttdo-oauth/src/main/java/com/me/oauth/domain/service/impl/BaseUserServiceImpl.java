package com.me.oauth.domain.service.impl;


import com.me.core.exception.CommonException;
import com.me.oauth.domain.entity.BasePasswordPolicy;
import com.me.oauth.domain.entity.BaseUser;
import com.me.oauth.domain.entity.BaseUserInfo;
import com.me.oauth.domain.repository.BasePasswordPolicyRepository;
import com.me.oauth.domain.repository.BaseUserInfoRepository;
import com.me.oauth.domain.repository.BaseUserRepository;
import com.me.oauth.domain.service.BaseUserService;
import com.me.oauth.domain.service.PasswordErrorTimesService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.Optional;

public class BaseUserServiceImpl implements BaseUserService {

    @Autowired
    private BaseUserRepository baseUserRepository;
    @Autowired
    private BasePasswordPolicyRepository basePasswordPolicyRepository;
    @Autowired
    private PasswordErrorTimesService passwordErrorTimesService;
    @Autowired
    private BaseUserInfoRepository baseUserInfoRepository;


    @Override
    public void lockUser(Long userId, Long tenantId) {
        BasePasswordPolicy passwordPolicy = basePasswordPolicyRepository.selectPasswordPolicy(tenantId);
        BaseUser user = null;
//                baseUserRepository.selectByPrimaryKey(userId);
        BaseUserInfo userInfo = null;
//                baseUserInfoRepository.selectByPrimaryKey(userId);

        if (user == null || userInfo == null) {
            throw new CommonException("hoth.warn.password.userNotFound");
        }

        long lockedExpiredTime = Optional.ofNullable(passwordPolicy.getLockedExpireTime()).orElse(Integer.MAX_VALUE);

        user.setLocked(true);
        user.setLockedUntilAt(new Date(System.currentTimeMillis() + lockedExpiredTime * 1000));
//        baseUserRepository.updateOptional(user, BaseUser.FIELD_LOCKED, BaseUser.FIELD_LOCKED_UNTIL_AT);

        userInfo.setLockedDate(new Date());
//        baseUserInfoRepository.updateOptional(userInfo, BaseUserInfo.FIELD_LOCKED_DATE);

        // 清除登录错误次数，使得从数据库更改之后可以登录
        passwordErrorTimesService.clearErrorTimes(userId);
    }

    @Override
    public void unLockUser(Long userId, Long tenantId) {
        BaseUser user = null;
//                baseUserRepository.selectByPrimaryKey(userId);
        BaseUserInfo userInfo = null;
//                baseUserInfoRepository.selectByPrimaryKey(userId);

        if (user == null || userInfo == null) {
            throw new CommonException("hoth.warn.password.userNotFound");
        }

        user.setLocked(false);
        user.setLockedUntilAt(null);
//        baseUserRepository.updateOptional(user, BaseUser.FIELD_LOCKED, BaseUser.FIELD_LOCKED_UNTIL_AT);

        userInfo.setLockedDate(null);
//        baseUserInfoRepository.updateOptional(userInfo, BaseUserInfo.FIELD_LOCKED_DATE);

        // 清除登录错误次数
        passwordErrorTimesService.clearErrorTimes(userId);
    }

}
