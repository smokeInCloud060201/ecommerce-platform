package com.karson.ecommerce.crmapi.services;

import com.karson.ecommerce.common.dtos.SearchDto;
import com.karson.ecommerce.common.exceptions.ResourceNotFoundException;
import com.karson.ecommerce.crmapi.dtos.permission.RoleDto;
import com.karson.ecommerce.crmapi.entity.Role;
import com.karson.ecommerce.crmapi.mapper.RoleMapper;
import com.karson.ecommerce.crmapi.repositories.RoleRepository;
import com.karson.ecommerce.crmapi.services.impl.RoleServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class RoleServiceTest {
    @Mock
    private RoleRepository roleRepository;

    @Mock
    private RoleMapper roleMapper;

    @InjectMocks
    private RoleServiceImpl roleService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testUpsertRole() {
        // Arrange
        RoleDto inputDto = new RoleDto();
        inputDto.setName("TEST_ROLE");

        Role existingRole = new Role();
        existingRole.setId(1L);
        existingRole.setName("TEST_ROLE");

        when(roleMapper.mapToEntity(inputDto)).thenReturn(existingRole);
        when(roleRepository.save(existingRole)).thenReturn(existingRole);
        when(roleMapper.mapToDto(existingRole)).thenReturn(inputDto);

        // Act
        RoleDto resultDto = roleService.upsertRole(inputDto);

        // Assert
        assertNotNull(resultDto);
        assertEquals("TEST_ROLE", resultDto.getName());
        verify(roleMapper, times(1)).mapToEntity(inputDto);
        verify(roleRepository, times(1)).save(existingRole);
        verify(roleMapper, times(1)).mapToDto(existingRole);
    }

    @Test
    void testDeleteRole() {
        // Arrange
        Long roleId = 1L;
        Role existingRole = new Role();
        existingRole.setId(roleId);

        when(roleRepository.findById(roleId)).thenReturn(Optional.of(existingRole));

        // Act
        assertDoesNotThrow(() -> roleService.deleteRole(roleId));

        // Assert
        verify(roleRepository, times(1)).delete(existingRole);
    }

    @Test
    void testDeleteRole_NotFound() {
        // Arrange
        Long roleId = 1L;

        when(roleRepository.findById(roleId)).thenReturn(Optional.empty());

        // Act and Assert
        assertThrows(ResourceNotFoundException.class, () -> roleService.deleteRole(roleId));

        // Verify that roleRepository.delete is never called
        verify(roleRepository, never()).delete(any(Role.class));
    }

    @Test
    void testGetRoles() {
        // Arrange
        Role role = new Role();
        role.setId(1L);
        role.setName("TEST_ROLE");

        when(roleRepository.findAll()).thenReturn(Collections.singletonList(role));
        when(roleMapper.mapToDto(role)).thenReturn(new RoleDto());

        // Act
        List<RoleDto> result = roleService.getRoles(new SearchDto());

        // Assert
        assertNotNull(result);
        assertFalse(result.isEmpty());
        verify(roleRepository, times(1)).findAll();
        verify(roleMapper, times(1)).mapToDto(role);
    }
}
