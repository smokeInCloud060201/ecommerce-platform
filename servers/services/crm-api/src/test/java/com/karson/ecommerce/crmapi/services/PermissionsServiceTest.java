package com.karson.ecommerce.crmapi.services;

import com.karson.ecommerce.common.exceptions.ResourceNotFoundException;
import com.karson.ecommerce.crmapi.dtos.permission.PermissionDto;
import com.karson.ecommerce.crmapi.entity.Permission;
import com.karson.ecommerce.crmapi.mapper.PermissionMapper;
import com.karson.ecommerce.crmapi.repositories.PermissionRepository;
import com.karson.ecommerce.crmapi.services.impl.PermissionServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class PermissionsServiceTest {
    @Mock
    private PermissionRepository permissionRepository;

    @Mock
    private PermissionMapper permissionMapper;

    @InjectMocks
    private PermissionServiceImpl permissionService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testUpsertPermission() {
        // Arrange
        PermissionDto inputDto = new PermissionDto();
        inputDto.setName("TEST_PERMISSION");

        Permission existingPermission = new Permission();
        existingPermission.setId(1L);
        existingPermission.setName("TEST_PERMISSION");

        when(permissionRepository.findByNameIgnoreCase("TEST_PERMISSION")).thenReturn(Optional.of(existingPermission));
        when(permissionRepository.save(any(Permission.class))).thenReturn(existingPermission);
        when(permissionMapper.mapToDto(existingPermission)).thenReturn(inputDto);

        // Act
        PermissionDto resultDto = permissionService.upsertPermission(inputDto);

        // Assert
        assertNotNull(resultDto);
        assertEquals("TEST_PERMISSION", resultDto.getName());
        verify(permissionRepository, times(1)).findByNameIgnoreCase("TEST_PERMISSION");
        verify(permissionRepository, times(1)).save(any(Permission.class));
        verify(permissionMapper, times(1)).mapToDto(existingPermission);
    }

    @Test
    void testDeletePermission() {
        // Arrange
        Long permissionId = 1L;
        Permission existingPermission = new Permission();
        existingPermission.setId(permissionId);

        when(permissionRepository.findById(permissionId)).thenReturn(Optional.of(existingPermission));
        when(permissionRepository.save(existingPermission)).thenReturn(existingPermission);

        // Act
        assertDoesNotThrow(() -> permissionService.deletePermission(permissionId));

        // Assert
        assertTrue(existingPermission.isDeleted());
        verify(permissionRepository, times(1)).findById(permissionId);
        verify(permissionRepository, times(1)).save(existingPermission);
    }

    @Test
    void testDeletePermission_NotFound() {
        // Arrange
        Long permissionId = 1L;

        when(permissionRepository.findById(permissionId)).thenReturn(Optional.empty());

        // Act and Assert
        assertThrows(ResourceNotFoundException.class, () -> permissionService.deletePermission(permissionId));

        // Verify that permissionRepository.save is never called
        verify(permissionRepository, never()).save(any(Permission.class));
    }

    @Test
    void testGetPermissions() {
        // Arrange
        Permission permission = new Permission();
        permission.setId(1L);
        permission.setName("TEST_PERMISSION");

        when(permissionRepository.findAll()).thenReturn(Collections.singletonList(permission));
        when(permissionMapper.mapToDto(permission)).thenReturn(new PermissionDto());

        // Act
        List<PermissionDto> result = permissionService.getPermissions();

        // Assert
        assertNotNull(result);
        assertFalse(result.isEmpty());
        verify(permissionRepository, times(1)).findAll();
        verify(permissionMapper, times(1)).mapToDto(permission);
    }

}
