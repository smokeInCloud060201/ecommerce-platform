package com.karson.ecommerce.common.configs.sercurity.context;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.security.authentication.AbstractAuthenticationToken;

import java.io.Serializable;


@EqualsAndHashCode(callSuper = true)
@Builder
@Data
public class ContextModel extends AbstractAuthenticationToken implements Serializable {

    private final transient AuthModel authModel;

    public ContextModel(AuthModel authModel) {
        super(authModel.getGrantedAuthoritySet());
        this.authModel = authModel;
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        return authModel.getUserPrincipal();
    }
}
