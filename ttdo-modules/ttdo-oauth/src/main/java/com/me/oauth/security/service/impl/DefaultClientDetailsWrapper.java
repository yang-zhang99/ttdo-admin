package com.me.oauth.security.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.me.oauth.domain.repository.ClientRepository;
import com.me.oauth.domain.vo.ClientRoleDetails;
import com.me.oauth.domain.vo.Role;
import com.me.oauth.security.service.ClientDetailsWrapper;
import com.me.core.oauth.CustomClientDetails;
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



/**
 * @author qingsheng.chen@hand-china.com
 */
public class DefaultClientDetailsWrapper implements ClientDetailsWrapper {
    private static final Logger logger = LoggerFactory.getLogger(DefaultClientDetailsWrapper.class);
    private ClientRepository clientRepository;

    public DefaultClientDetailsWrapper(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    @Override
    public void warp(CustomClientDetails details, Long clientId, Long tenantId) {
        logger.debug(">>>>> Before warp[{},{}] : {}", clientId, tenantId, details);
        Map<Long, List<Role>> clientRoleMap = clientRepository.selectRoleDetails(clientId)
                .stream()
                .collect(Collectors.toMap(ClientRoleDetails::getTenantId, ClientRoleDetails::getRoles));
        if (clientRoleMap.containsKey(tenantId)) {
            details.setCurrentTenantId(tenantId);
            details.setCurrentRoleId(clientRoleMap.get(tenantId)
                    .stream()
                    .findFirst().orElse(new Role())
                    .getId());
            details.setRoleIds(clientRoleMap.get(tenantId).stream().map(Role::getId).collect(Collectors.toList()));
        }

        if (CollectionUtils.isEmpty(details.getRoleIds())) {
            logger.warn("Client not assign any role! clientId: {}", details.getClientId());
        }

        details.setTenantIds(new ArrayList<>(clientRoleMap.keySet()));
        logger.debug(">>>>> After warp[{},{}] : {}", clientId, tenantId, details);

    }
}
