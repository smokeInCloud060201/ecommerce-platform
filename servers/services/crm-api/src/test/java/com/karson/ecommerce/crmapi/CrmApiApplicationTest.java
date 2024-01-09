package com.karson.ecommerce.crmapi;

import com.karson.ecommerce.common.configs.sercurity.GlobalAuthenticateEntrypoint;
import com.karson.ecommerce.common.configs.taks.TaskGlobalExecutorConfiguration;
import com.karson.ecommerce.crmapi.clients.notifications.email.EmailClient;
import com.karson.ecommerce.crmapi.configs.security.CrmFilter;
import com.karson.ecommerce.crmapi.configs.security.CrmSecurityConfig;
import com.karson.ecommerce.crmapi.configs.security.SecurityConfigProperties;
import com.karson.ecommerce.crmapi.configs.security.SecurityGlobalConfig;
import com.karson.ecommerce.crmapi.mapper.PermissionMapper;
import com.karson.ecommerce.crmapi.mapper.RoleMapper;
import com.karson.ecommerce.crmapi.mapper.UserMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class CrmApiApplicationTest {
    @Autowired
    EmailClient emailClient;

    @Autowired
    CrmFilter crmFilter;

    @Autowired
    CrmSecurityConfig crmSecurityConfig;

    @Autowired
    SecurityConfigProperties securityConfigProperties;

    @Autowired
    SecurityGlobalConfig securityGlobalConfig;

    @Autowired
    PermissionMapper permissionMapper;

    @Autowired
    RoleMapper roleMapper;

    @Autowired
    UserMapper userMapper;

    @Autowired
    GlobalAuthenticateEntrypoint globalAuthenticateEntrypoint;

    @Test
    void shouldInjectToContext() {
        Assertions.assertNotNull(emailClient);
        Assertions.assertNotNull(crmFilter);
        Assertions.assertNotNull(crmSecurityConfig);
        Assertions.assertNotNull(securityConfigProperties);
        Assertions.assertNotNull(securityGlobalConfig);
        Assertions.assertNotNull(roleMapper);
        Assertions.assertNotNull(userMapper);
        Assertions.assertNotNull(globalAuthenticateEntrypoint);
    }
}
