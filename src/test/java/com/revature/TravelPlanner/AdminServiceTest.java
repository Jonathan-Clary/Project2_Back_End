package com.revature.TravelPlanner;

import com.revature.DAOs.AdminDAO;
import com.revature.exceptions.AdminNotFoundException;
import com.revature.models.Admin;
import com.revature.services.AdminService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AdminServiceTest {

    @Mock
    private AdminDAO adminDAO;

    @InjectMocks
    private AdminService adminService;

    @Test
    public void testGetAdminById() throws Exception {
        //given
        Admin admin = mock(Admin.class);

        when(adminDAO.findById(admin.getAdminId())).thenReturn(Optional.of(admin));

        //when
        Admin returningAdmin = adminService.getAdminById(admin.getAdminId());

        //then
        assertEquals(admin, returningAdmin);
        verify(adminDAO, times(1)).findById(admin.getAdminId());

    }

    @Test
    public void testAdminNotFoundException(){
        //given
        final UUID id = UUID.randomUUID();

        when(adminDAO.findById(id)).thenReturn(Optional.empty());

        //when
        AdminNotFoundException thrown = assertThrows(
                AdminNotFoundException.class, () -> adminService.getAdminById(id)
        );

        //then
        assertTrue(thrown.getMessage().contains("Admin with Id: " + id + " Not Found."));
        verify(adminDAO, times(1)).findById(id);

    }

}
