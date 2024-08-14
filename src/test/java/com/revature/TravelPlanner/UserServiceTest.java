package com.revature.TravelPlanner;

import com.revature.DAOs.UserDAO;
import com.revature.exceptions.CustomException;
import com.revature.exceptions.InvalidIDException;
import com.revature.exceptions.InvalidUserException;
import com.revature.exceptions.UserNotFoundException;
import com.revature.models.User;
import com.revature.services.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashMap;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    UserDAO userDAO;

    @InjectMocks
    UserService userService;


    @Test
    public void testUpdateUser() throws CustomException {
        User user = new User(1, "fname", "lname", "password", "test@test.test", "2024-08-14");
        User new_user = new User(1, "new first name", "new last name", "password", "test@test.test", "2024-08-14");

        when(userDAO.findById(1)).thenReturn(Optional.of(user));
        when(userDAO.findById(2)).thenReturn(Optional.empty()); // User doesn't exist
        when(userDAO.save(new_user)).thenReturn(new_user);

        // passing case
        HashMap<String, String> map = new HashMap<>();
        map.put("firstName", "new first name");
        map.put("lastName", "new last name");
        User updated_user = userService.updateUserById(1,map);

        assertEquals(updated_user, new_user);

        // failing cases
        HashMap<String, String> invalid_map = new HashMap<>();
        invalid_map.put("firstName", "");
        invalid_map.put("email", "123");

        assertThrows(InvalidIDException.class, () -> userService.updateUserById(-1,map));
        assertThrows(InvalidUserException.class, () -> userService.updateUserById(1,invalid_map));
        assertThrows(UserNotFoundException.class, () -> userService.updateUserById(2,invalid_map));

        verify(userDAO, times(2)).findById(1);
        verify(userDAO, times(0)).findById(-1);
        verify(userDAO, times(1)).findById(2);
        verify(userDAO, times(1)).save(any(User.class));
    }
}
