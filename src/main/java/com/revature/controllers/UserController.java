package com.revature.controllers;


import com.revature.exceptions.CustomException;
import com.revature.models.User;
import com.revature.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/user")
@CrossOrigin
public class UserController {

    @Autowired
    AuthController authController;

    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }


    @PostMapping("/register")
    public ResponseEntity<User> createUser(@RequestBody @Valid User user)throws CustomException{
        User returningUser =  userService.createUser(user);
        return ResponseEntity.status(201).body(returningUser);
    }

    @PatchMapping
    public ResponseEntity<Object> updateLoggedInUserProfile(@RequestBody Map<String,String> newUser) throws CustomException {
        var user = userService.updateUserById(loggedInUserId(), newUser);
        return ResponseEntity.ok(user);
    }

    // handles all the custom exceptions
    @ExceptionHandler(CustomException.class)
    public ResponseEntity<Object> handleCustomException( CustomException e){
        return ResponseEntity.status(e.getStatus()).body(e.getMsg());
    }

    private UUID loggedInUserId() throws CustomException {
        // later we'll use the ID that's in the Token
        User authUser = authController.getAuthenticatedUser();
        if(authUser != null){
            return authUser.getUserId();
        }
        // TODO: Please check if is that what you want to do
        return null;
    }
}
