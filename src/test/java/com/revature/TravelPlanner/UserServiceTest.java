package com.revature.TravelPlanner;

import com.revature.DAOs.UserDAO;
import com.revature.exceptions.*;
import com.revature.models.User;
import com.revature.services.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashMap;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    UserDAO userDAO;

    @InjectMocks
    UserService userService;

    //TODO::Refactor Test
    @Test
    public void testUpdateUser_Successful() throws CustomException {

        UUID userId = UUID.randomUUID();
        User user = new User();
        user.setUserId(userId);
        user.setFirstName("old first name");
        user.setLastName("old last name");
        user.setEmail("old@test.net");

        HashMap<String, String> map = new HashMap<>();
        map.put("firstName", "new first name");
        map.put("lastName", "new last name");

        when(userDAO.findById(userId)).thenReturn(Optional.of(user));
        when(userDAO.save(any(User.class))).thenReturn(user);

        User updatedUser = userService.updateUserById(userId, map);

        assertNotNull(updatedUser);
        assertEquals("new first name", updatedUser.getFirstName());
        assertEquals("new last name", updatedUser.getLastName());
        assertEquals("old@test.net", updatedUser.getEmail());
        verify(userDAO, times(1)).save(user);
    }

    @Test
    void testUpdateUser_EmailAlreadyExists() {

        UUID userId = UUID.randomUUID();
        User user = new User();
        user.setUserId(userId);
        user.setEmail("old@test.net");

        HashMap<String, String> map = new HashMap<>();
        map.put("email", "out4@harambe.com");

        User existingUser = new User();
        existingUser.setUserId(UUID.randomUUID());
        existingUser.setEmail("out4@harambe.com");

        when(userDAO.findById(userId)).thenReturn(Optional.of(user));
        when(userDAO.findByEmail("out4@harambe.com")).thenReturn(Optional.of(existingUser));

        assertThrows(EmailAlreadyExistException.class, () -> {
            userService.updateUserById(userId, map);
        });
        verify(userDAO, never()).save(any(User.class));
    }

}
