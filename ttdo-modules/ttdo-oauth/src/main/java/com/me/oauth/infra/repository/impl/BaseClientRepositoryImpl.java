package com.me.oauth.infra.repository.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.me.oauth.domain.entity.BaseClient;
import com.me.oauth.domain.repository.BaseClientRepository;
import com.me.oauth.infra.mapper.BaseClientMapper;
import com.me.common.HZeroService;
import com.me.redis.RedisHelper;
import com.me.redis.safe.SafeRedisHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

/**
 * @author bojiangzhou 2019/08/06
 */
@Component
public class BaseClientRepositoryImpl implements BaseClientRepository {

    private static final String CLIENT_KEY = HZeroService.Oauth.CODE + ":client";

    private final RedisHelper redisHelper;

    @Autowired
    private BaseClientMapper baseClientMapper;

    public BaseClientRepositoryImpl(RedisHelper redisHelper) {
        this.redisHelper = redisHelper;
    }

    @Override
    public BaseClient selectClient(String clientName) {
        Assert.notNull(clientName, "clientName not be null.");

//        String str = SafeRedisHelper.execute(HZeroService.Oauth.REDIS_DB,
//                () -> redisHelper.hshGet(CLIENT_KEY, clientName));
        BaseClient client = null;
//        if (StringUtils.isNotBlank(str)) {
//            client = redisHelper.fromJson(str, BaseClient.class);
//        } else {
            BaseClient param = new BaseClient();
            param.setName("hzero-front-dev");
            QueryWrapper queryWrapper = new QueryWrapper();
            queryWrapper.eq("name", clientName);
            client = baseClientMapper.selectOne(queryWrapper);
//            if (client != null) {
//                BaseClient finalClient = client;
//                SafeRedisHelper.execute(HZeroService.Oauth.REDIS_DB,
//                        () -> redisHelper.hshPut(CLIENT_KEY, clientName, redisHelper.toJson(finalClient)));
//            }
//        }
//        Assert.notNull(client, "error.client.notFound");
//        if (Objects.equals(client.getEnabledFlag(), BaseConstants.Flag.NO)) {
//            throw new AuthenticationServiceException("hoth.warn.password.client.disabled");
//        }
        return client;
    }

    @Override
    public void saveClient(BaseClient client) {
        Assert.notNull(client, "client not be null.");
        SafeRedisHelper.execute(HZeroService.Oauth.REDIS_DB, redisHelper,
                () -> redisHelper.hshPut(CLIENT_KEY, client.getName(), redisHelper.toJson(client)));
    }

    @Override
    public void removeClient(String clientName) {
        Assert.notNull(clientName, "clientName not be null.");
        SafeRedisHelper.execute(HZeroService.Oauth.REDIS_DB,
                () -> redisHelper.hshDelete(CLIENT_KEY, clientName));
    }
}
