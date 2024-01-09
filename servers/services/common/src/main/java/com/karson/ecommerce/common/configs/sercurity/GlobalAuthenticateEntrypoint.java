package com.karson.ecommerce.common.configs.sercurity;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.karson.ecommerce.common.dtos.ResponseDto;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.OutputStream;

import static com.karson.ecommerce.common.constants.ResponseErrorCode.UN_AUTHORIZE;

@Component
public class GlobalAuthenticateEntrypoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {
        ResponseDto<Object> responseDto = ResponseDto.builder()
                .errorCode(UN_AUTHORIZE)
                .errorMessage("UnAuthorize cause " + authException.getMessage())
                .statusCode(401)
                .build();
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        OutputStream responseStream = response.getOutputStream();
        ObjectMapper mapper = new ObjectMapper();
        mapper.findAndRegisterModules();
        mapper.writeValue(responseStream, responseDto);
        responseStream.flush();
    }
}
