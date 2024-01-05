package com.karson.ecommerce.crmapi.services.impl;

import com.karson.ecommerce.common.configs.sercurity.context.AuthModel;
import com.karson.ecommerce.common.configs.sercurity.context.ContextModel;
import com.karson.ecommerce.common.dtos.SearchDto;
import com.karson.ecommerce.crmapi.dtos.TokenDto;
import com.karson.ecommerce.common.exceptions.ResourceNotFoundException;
import com.karson.ecommerce.crmapi.clients.notifications.email.EmailClient;
import com.karson.ecommerce.crmapi.clients.notifications.email.EmailDto;
import com.karson.ecommerce.crmapi.configs.security.JwtService;
import com.karson.ecommerce.crmapi.dtos.auth.LoginRequestDto;
import com.karson.ecommerce.crmapi.dtos.auth.UserRegisterRequestDto;
import com.karson.ecommerce.crmapi.dtos.user.UserResponseDto;
import com.karson.ecommerce.crmapi.entity.Permission;
import com.karson.ecommerce.crmapi.entity.Role;
import com.karson.ecommerce.crmapi.entity.User;
import com.karson.ecommerce.crmapi.enums.NotificationEmailType;
import com.karson.ecommerce.crmapi.mapper.UserMapper;
import com.karson.ecommerce.crmapi.repositories.RoleRepository;
import com.karson.ecommerce.crmapi.repositories.UserRepository;
import com.karson.ecommerce.crmapi.services.UserService;
import java.util.Collections;

import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final RoleRepository roleRepository;
    private final JwtService jwtService;
    private final EmailClient emailClient;

    private static final String NOT_FOUND_USER = "Not found user";
    private static final String OTP_MAIL_KEY = "otp";

    @Override
    public UserResponseDto upsertUser(UserRegisterRequestDto userRegisterRequestDto) {
        User user = userMapper.mapToUser(userRegisterRequestDto);
        user.setPassword(passwordEncoder.encode(userRegisterRequestDto.getPassword()));
        userRepository.save(user);
        return userMapper.mapToDto(user);
    }

    @Override
    public UserResponseDto findByUserName(String userName) throws ResourceNotFoundException {
        User user = userRepository.findByUsername(userName).orElseThrow(() -> new ResourceNotFoundException(NOT_FOUND_USER));
        return userMapper.mapToDto(user);
    }

    @Override
    public UserResponseDto loadUserByToken() {
        User userDetails = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userMapper.mapToDto(userDetails);
    }

    @Override
    public void deleteUser(Long userId) throws ResourceNotFoundException {
        userRepository.delete(
                userRepository.findById(userId)
                        .orElseThrow(() -> new ResourceNotFoundException(NOT_FOUND_USER))
        );
    }

    @Override
    public UserResponseDto findByUserId(Long userId) throws ResourceNotFoundException {
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException(NOT_FOUND_USER));
        return userMapper.mapToDto(user);
    }

    @Override
    public List<UserResponseDto> searchUser(SearchDto searchDto) {
        return Collections.emptyList();
    }

    public TokenDto login(LoginRequestDto loginRequest) throws ResourceNotFoundException {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginRequest.getUserName(),
                loginRequest.getPassword()
        ));
        User user = userRepository.findByUsername(loginRequest.getUserName())
                .orElseThrow(() -> new ResourceNotFoundException("Not exists User"));

        ContextModel contextModel = ContextModel.builder()
                .authModel(AuthModel.builder()
                        .userPrincipal(AuthModel.UserPrincipal.builder()
                                .roles(user.getRoles().stream()
                                        .map(Role::getName)
                                        .collect(Collectors.toSet()))
                                .permissions(user.getRoles().stream()
                                        .flatMap(role -> role.getPermissionsRole().stream()
                                                .map(Permission::getName))
                                        .collect(Collectors.toSet()))
                                .email(user.getEmail())
                                .isEnabled(user.isEnabled())
                                .isAccountNonExpired(user.isAccountNonExpired())
                                .isAccountNonLocked(user.isAccountNonLocked())
                                .isCredentialsNonExpired(user.isCredentialsNonExpired())
                                .build())
                        .grantedAuthoritySet(new HashSet<>(user.getAuthorities()))
                        .build())
                .build();

        String accessToken = jwtService.generateAccessToken(contextModel);
        String refreshToken = jwtService.generateRefreshToken(contextModel);

        return TokenDto.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    @Override
    public UserResponseDto addRoleToUser(Long roleId) throws ResourceNotFoundException {
        AuthModel authModel = (AuthModel) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String userName = authModel.getUserPrincipal().getEmail();
        User user = userRepository.findByUsername(userName).orElseThrow(() -> new ResourceNotFoundException("User not found"));
        Role role = roleRepository.findById(roleId).orElseThrow(() -> new ResourceNotFoundException("Role not found"));
        Set<Role> roles = user.getRoles();
        roles.add(role);
        user.setRoles(roles);
        user = userRepository.save(user);
        return userMapper.mapToDto(user);
    }

    @Async
    public void sendOtpMessage(String emailAddress) {
        Map<String, String> parameters = new HashMap<String, String>();
        parameters.put(OTP_MAIL_KEY, generateOTP());
        EmailDto emailTemplate = EmailDto.builder()
                .emailType(NotificationEmailType.OTP)
                .parameters(parameters)
                .build();
        emailClient.sendEmail(emailAddress, emailTemplate);
    }

    private String generateOTP() {
        int otpLength = 6;

        StringBuilder otpBuilder = new StringBuilder();

        for (int i = 0; i < otpLength; i++) {
            int digit = ThreadLocalRandom.current().nextInt(10);
            otpBuilder.append(digit);
        }

        return otpBuilder.toString();
    }
}
