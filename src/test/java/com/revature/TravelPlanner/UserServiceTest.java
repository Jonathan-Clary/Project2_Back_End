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

    //TODO::Refactor Test
    @Test
    public void testUpdateUser() throws CustomException {
//        User user = new User(1, "fname", "lname", "password", "test@test.test", 123);
//        User new_user = new User(1, "new first name", "new last name", "password", "test@test.test", 123);
//        // to test already existing emails
//        User other_user = new User(3, "fname", "lname", "password", "new@test.test", 123);
//
//        when(userDAO.findById(1)).thenReturn(Optional.of(user));
//        when(userDAO.findById(2)).thenReturn(Optional.empty()); // User doesn't exist
//        when(userDAO.findByEmail("test@test.test")).thenReturn(Optional.of(user));
//        when(userDAO.findByEmail("new@test.test")).thenReturn(Optional.of(other_user));
//        when(userDAO.save(new_user)).thenReturn(new_user);
//
//        // passing case
//        HashMap<String, String> map = new HashMap<>();
//        map.put("firstName", "new first name");
//        map.put("lastName", "new last name");
//        User updated_user = userService.updateUserById(1,map);
//
//        assertEquals(updated_user, new_user);
//
//        // failing cases
//        HashMap<String, String> existing_email_map = new HashMap<>();
//        existing_email_map.put("email", "new@test.test"); // other user's email
//
//        HashMap<String, String> invalid_values_map = new HashMap<>();
//        invalid_values_map.put("firstName", "");
//        invalid_values_map.put("email", "123");
//
//
//        assertThrows(InvalidIDException.class, () -> userService.updateUserById(-1,map));
//        assertThrows(EmailAlreadyExistException.class, () -> userService.updateUserById(1,existing_email_map));
//        assertThrows(InvalidUserException.class, () -> userService.updateUserById(1,invalid_values_map));
//        assertThrows(UserNotFoundException.class, () -> userService.updateUserById(2,map));
//
//
//        verify(userDAO, times(3)).findById(1);
//        verify(userDAO, times(0)).findById(-1);
//        verify(userDAO, times(1)).findById(2);
//        verify(userDAO, times(1)).save(any(User.class));
    }
}
