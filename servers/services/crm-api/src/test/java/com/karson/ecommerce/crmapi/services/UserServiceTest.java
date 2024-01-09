package com.karson.ecommerce.crmapi.services;

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
import com.karson.ecommerce.crmapi.entity.Role;
import com.karson.ecommerce.crmapi.entity.User;
import com.karson.ecommerce.crmapi.mapper.UserMapper;
import com.karson.ecommerce.crmapi.repositories.OTPRepository;
import com.karson.ecommerce.crmapi.repositories.RoleRepository;
import com.karson.ecommerce.crmapi.repositories.UserRepository;
import com.karson.ecommerce.crmapi.services.impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class UserServiceTest {
    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private JwtService jwtService;

    @Mock
    private NotificationService notificationService;

    @Mock
    private OTPRepository otpRepository;

    @InjectMocks
    private UserServiceImpl userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testUpsertUser() {
        // Arrange
        UserRegisterRequestDto userRegisterRequestDto = new UserRegisterRequestDto();
        User user = new User();
        user.setId(1L);
        user.setEmail("testUser");

        when(userMapper.mapToUser(userRegisterRequestDto)).thenReturn(user);
        when(passwordEncoder.encode(userRegisterRequestDto.getPassword())).thenReturn("encodedPassword");
        doNothing().when(notificationService).sendOtpMessage(any(User.class));

        UserResponseDto userResponseDto = new UserResponseDto();
        userResponseDto.setEmail("testUser");
        when(userMapper.mapToDto(any())).thenReturn(userResponseDto);
        // Act
        UserResponseDto resultDto = userService.upsertUser(userRegisterRequestDto);

        // Assert
        assertNotNull(resultDto);
        assertEquals("testUser", resultDto.getEmail());
        verify(userRepository, times(1)).save(user);
        verify(notificationService, times(1)).sendOtpMessage(user);
    }

    @Test
    void testFindByUserName() throws ResourceNotFoundException {
        // Arrange
        String userName = "testUser";

        User user = new User();
        user.setEmail(userName);
        UserResponseDto userResponseDto = new UserResponseDto();
        userResponseDto.setEmail(userName);
        when(userMapper.mapToDto(any())).thenReturn(userResponseDto);

        when(userRepository.findByUsername(userName)).thenReturn(Optional.of(user));

        // Act
        UserResponseDto resultDto = userService.findByUserName(userName);

        // Assert
        assertNotNull(resultDto);
        assertEquals(userName, resultDto.getEmail());
        verify(userRepository, times(1)).findByUsername(userName);
        verify(userMapper, times(1)).mapToDto(user);
    }

    @Test
    void testFindByUserName_NotFound() {
        // Arrange
        when(userRepository.findByUsername("nonExistingUser")).thenReturn(Optional.empty());

        // Act and Assert
        assertThrows(ResourceNotFoundException.class, () -> userService.findByUserName("nonExistingUser"));
        verify(userRepository, times(1)).findByUsername("nonExistingUser");
        verify(userMapper, never()).mapToDto(any(User.class));
    }

    @Test
    void testLoadUserByToken() {
        // Arrange
        User user = new User();
        user.setEmail("testUser");
        SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(user, null));

        when(userMapper.mapToDto(user)).thenReturn(new UserResponseDto());
        UserResponseDto userResponseDto = new UserResponseDto();
        userResponseDto.setEmail("testUser");
        when(userMapper.mapToDto(any())).thenReturn(userResponseDto);
        // Act
        UserResponseDto resultDto = userService.loadUserByToken();

        // Assert
        assertNotNull(resultDto);
        assertEquals("testUser", resultDto.getEmail());
        verify(userMapper, times(1)).mapToDto(user);
    }

    @Test
    void testDeleteUser() {
        // Arrange
        User user = new User();
        user.setId(1L);

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        doNothing().when(userRepository).delete(user);

        // Act
        assertDoesNotThrow(() -> userService.deleteUser(1L));

        // Assert
        verify(userRepository, times(1)).delete(user);
    }

    @Test
    void testDeleteUser_NotFound() {
        // Arrange
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        // Act and Assert
        assertThrows(ResourceNotFoundException.class, () -> userService.deleteUser(1L));
        verify(userRepository, never()).delete(any(User.class));
    }

    @Test
    void testFindByUserId() throws ResourceNotFoundException {
        // Arrange
        User user = new User();
        user.setId(1L);
        user.setEmail("testUser");

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        UserResponseDto userResponseDto = new UserResponseDto();
        userResponseDto.setEmail("testUser");
        when(userMapper.mapToDto(any())).thenReturn(userResponseDto);

        // Act
        UserResponseDto resultDto = userService.findByUserId(1L);

        // Assert
        assertNotNull(resultDto);
        assertEquals("testUser", resultDto.getEmail());
        verify(userRepository, times(1)).findById(1L);
        verify(userMapper, times(1)).mapToDto(user);
    }

    @Test
    void testFindByUserId_NotFound() {
        // Arrange
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        // Act and Assert
        assertThrows(ResourceNotFoundException.class, () -> userService.findByUserId(1L));
        verify(userRepository, times(1)).findById(1L);
        verify(userMapper, never()).mapToDto(any(User.class));
    }

    @Test
    void testSearchUser() {
        // Arrange
        SearchDto searchDto = new SearchDto();

        // Act
        List<UserResponseDto> result = userService.searchUser(searchDto);

        // Assert
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void testLogin() throws ResourceNotFoundException {
        // Arrange
        LoginRequestDto loginRequestDto = new LoginRequestDto();
        loginRequestDto.setUserName("testUser");
        loginRequestDto.setPassword("password");

        User user = new User();
        user.setEmail("testUser");
        user.setPassword(passwordEncoder.encode("password"));
        user.setVerified(true);
        Set<Role> roles = new HashSet<>();
        Role role = new Role();
        role.setName("ROLE_USER");
        roles.add(role);
        user.setRoles(roles);

        Authentication authentication = mock(Authentication.class);
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenReturn(authentication);
        when(authentication.getPrincipal()).thenReturn(user);

        when(userRepository.findByUsername("testUser")).thenReturn(Optional.of(user));

        when(jwtService.generateAccessToken(any())).thenReturn("accessToken");
        when(jwtService.generateRefreshToken(any())).thenReturn("refreshToken");

        UserResponseDto userResponseDto = new UserResponseDto();
        userResponseDto.setEmail("testUser");
        when(userMapper.mapToDto(any())).thenReturn(userResponseDto);

        // Act
        TokenDto resultDto = userService.login(loginRequestDto);

        // Assert
        assertNotNull(resultDto);
        assertEquals("accessToken", resultDto.getAccessToken());
        assertEquals("refreshToken", resultDto.getRefreshToken());
        verify(authenticationManager, times(1)).authenticate(any(UsernamePasswordAuthenticationToken.class));
        verify(jwtService, times(1)).generateAccessToken(any(ContextModel.class));
        verify(jwtService, times(1)).generateRefreshToken(any(ContextModel.class));
    }

    @Test
    void testLogin_UserNotVerified() {
        // Arrange
        LoginRequestDto loginRequestDto = new LoginRequestDto();
        loginRequestDto.setUserName("testUser");
        loginRequestDto.setPassword("password");

        User user = new User();
        user.setEmail("testUser");
        user.setPassword(passwordEncoder.encode("password"));
        user.setVerified(false);

        when(userRepository.findByUsername("testUser")).thenReturn(Optional.of(user));

        // Act and Assert
        assertThrows(BadCredentialsException.class, () -> userService.login(loginRequestDto));
        verify(authenticationManager, never()).authenticate(any(UsernamePasswordAuthenticationToken.class));
        verify(jwtService, never()).generateAccessToken(any(ContextModel.class));
        verify(jwtService, never()).generateRefreshToken(any(ContextModel.class));
    }

    @Test
    void testAddRoleToUser() throws ResourceNotFoundException {
        // Arrange
        AuthModel.UserPrincipal userPrincipal = new AuthModel.UserPrincipal();
        userPrincipal.setEmail("testUser");
        userPrincipal.setRoles(new HashSet<>());

        AuthModel authModel = new AuthModel();
        authModel.setUserPrincipal(userPrincipal);

        SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(authModel, null));

        User user = new User();
        user.setEmail("testUser");
        user.setRoles(new HashSet<>());

        Role role = new Role();
        role.setId(1L);
        role.setName("ROLE_USER");

        when(userRepository.findByUsername("testUser")).thenReturn(Optional.of(user));
        when(roleRepository.findById(1L)).thenReturn(Optional.of(role));
        when(userRepository.save(user)).thenReturn(user);
        when(userMapper.mapToDto(user)).thenReturn(new UserResponseDto());

        // Act
        UserResponseDto resultDto = userService.addRoleToUser(1L);

        // Assert
        assertNotNull(resultDto);
        verify(userRepository, times(1)).findByUsername("testUser");
        verify(roleRepository, times(1)).findById(1L);
        verify(userRepository, times(1)).save(user);
        verify(userMapper, times(1)).mapToDto(user);
    }

    @Test
    void testVerifyOTP() {
        // Arrange
        OTP otp = new OTP();
        otp.setCode("123456");
        otp.setUserOtp(new User());
        otp.setExpiredAt(LocalDateTime.now().plusMinutes(3));

        when(otpRepository.findOtpByCode("123456", "test@example.com")).thenReturn(Optional.of(otp));
        when(userRepository.findByUsername("test@example.com")).thenReturn(Optional.of(new User()));

        // Act and Assert
        assertDoesNotThrow(() -> userService.verifyOTP("test@example.com", "123456"));
        verify(otpRepository, times(1)).findOtpByCode("123456", "test@example.com");
        verify(userRepository, times(1)).findByUsername("test@example.com");
        verify(otpRepository, times(1)).save(otp);
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void testVerifyOTP_InvalidCode() {
        // Arrange
        when(otpRepository.findOtpByCode("invalidCode", "test@example.com")).thenReturn(Optional.empty());

        // Act and Assert
        assertThrows(BadRequestException.class, () -> userService.verifyOTP("test@example.com", "invalidCode"));
        verify(otpRepository, times(1)).findOtpByCode("invalidCode", "test@example.com");
        verify(userRepository, never()).findByUsername(anyString());
        verify(otpRepository, never()).save(any(OTP.class));
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    void testVerifyOTP_ExpiredCode() {
        // Arrange
        OTP expiredOtp = new OTP();
        expiredOtp.setCode("123456");
        expiredOtp.setUserOtp(new User());
        expiredOtp.setExpiredAt(LocalDateTime.now().minusMinutes(1));

        when(otpRepository.findOtpByCode("123456", "test@example.com")).thenReturn(Optional.of(expiredOtp));

        // Act and Assert
        assertThrows(BadRequestException.class, () -> userService.verifyOTP("test@example.com", "123456"));
        verify(otpRepository, times(1)).findOtpByCode("123456", "test@example.com");
        verify(userRepository, never()).findByUsername(anyString());
        verify(otpRepository, never()).save(any(OTP.class));
        verify(userRepository, never()).save(any(User.class));
    }
}
