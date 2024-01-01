package com.karson.ecommerce.common.configs.sercurity.context;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.authentication.AbstractAuthenticationToken;


@Builder
@Getter
@Setter
public class ContextModel extends AbstractAuthenticationToken {

    private final AuthModel authModel;

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
