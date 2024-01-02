package com.karson.ecommerce.crmapi.mapper;

import com.karson.ecommerce.crmapi.dtos.auth.UserRegisterRequestDto;
import com.karson.ecommerce.crmapi.dtos.user.UserResponseDto;
import com.karson.ecommerce.crmapi.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {

    PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Mapping(source = "password", target = "password", qualifiedByName = "EncodePassword")
    User mapToUser(UserRegisterRequestDto userDto);
    UserResponseDto mapToDto(User user);

    @Named("EncodePassword")
    static String encodePassword(String password) {
        return passwordEncoder.encode(password);
    }
}
