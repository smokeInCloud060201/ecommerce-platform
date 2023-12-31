package com.karson.ecommerce.crmapi.services.impl;

import com.karson.ecommerce.common.configs.sercurity.context.AuthModel;
import com.karson.ecommerce.common.configs.sercurity.context.ContextModel;
import com.karson.ecommerce.common.dtos.SearchDto;
import com.karson.ecommerce.common.exceptions.BadRequestException;
import com.karson.ecommerce.common.exceptions.ResourceNotFoundException;
import com.karson.ecommerce.crmapi.configs.security.JwtService;
import com.karson.ecommerce.crmapi.dtos.TokenDto;
import com.karson.ecommerce.crmapi.dtos.auth.LoginRequestDto;
import com.karson.ecommerce.crmapi.dtos.auth.UserRegisterRequestDto;
import com.karson.ecommerce.crmapi.dtos.user.UserResponseDto;
import com.karson.ecommerce.crmapi.entity.OTP;
import com.karson.ecommerce.crmapi.entity.Permission;
import com.karson.ecommerce.crmapi.entity.Role;
import com.karson.ecommerce.crmapi.entity.User;
import com.karson.ecommerce.crmapi.mapper.UserMapper;
import com.karson.ecommerce.crmapi.repositories.OTPRepository;
import com.karson.ecommerce.crmapi.repositories.RoleRepository;
import com.karson.ecommerce.crmapi.repositories.UserRepository;
import com.karson.ecommerce.crmapi.services.NotificationService;
import com.karson.ecommerce.crmapi.services.UserService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
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
    private final NotificationService notificationService;
    private final OTPRepository otpRepository;

    private static final String NOT_FOUND_USER = "Not found user";


    @Override
    @Transactional
    public UserResponseDto upsertUser(UserRegisterRequestDto userRegisterRequestDto) {
        User user = userMapper.mapToUser(userRegisterRequestDto);
        user.setPassword(passwordEncoder.encode(userRegisterRequestDto.getPassword()));
        userRepository.save(user);
        notificationService.sendOtpMessage(user);
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
        userRepository.delete(userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException(NOT_FOUND_USER)));
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
        User user = userRepository.findByUsername(loginRequest.getUserName()).orElseThrow(() -> new ResourceNotFoundException("Not exists User"));

        if (!user.isVerified()) {
            throw new BadCredentialsException("Please verify your account first");
        }
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUserName(), loginRequest.getPassword()));


        ContextModel contextModel = ContextModel.builder().authModel(AuthModel.builder().userPrincipal(AuthModel.UserPrincipal.builder().roles(user.getRoles().stream().map(Role::getName).collect(Collectors.toSet())).permissions(user.getRoles().stream().flatMap(role -> role.getPermissionsRole().stream().map(Permission::getName)).collect(Collectors.toSet())).email(user.getEmail()).isVerified(user.isVerified()).isEnabled(user.isEnabled()).isAccountNonExpired(user.isAccountNonExpired()).isAccountNonLocked(user.isAccountNonLocked()).isCredentialsNonExpired(user.isCredentialsNonExpired()).build()).grantedAuthoritySet(new HashSet<>(user.getAuthorities())).build()).build();

        String accessToken = jwtService.generateAccessToken(contextModel);
        String refreshToken = jwtService.generateRefreshToken(contextModel);
        TokenDto tokenDto = new TokenDto();
        tokenDto.setAccessToken(accessToken);
        tokenDto.setRefreshToken(refreshToken);

        return tokenDto;
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

    @Override
    public void verifyOTP(String emailAddress, String otpCode) throws BadRequestException, ResourceNotFoundException {
        OTP otp = otpRepository.findOtpByCode(otpCode, emailAddress).orElse(null);
        if (otp != null && LocalDateTime.now().isBefore(otp.getExpiredAt())) {
            User user = userRepository.findByUsername(emailAddress).orElseThrow(() -> new ResourceNotFoundException("Not found user"));
            otp.setDeleted(true);
            user.setVerified(true);
            otpRepository.save(otp);
            userRepository.save(user);
            return;
        }
        throw new BadRequestException("Failed");
    }
}
