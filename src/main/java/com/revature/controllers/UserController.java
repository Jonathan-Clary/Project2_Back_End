package com.revature.controllers;


import com.revature.exceptions.CustomException;
import com.revature.models.User;
import com.revature.services.UserService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/user")
@CrossOrigin
public class UserController {
    Logger log = LoggerFactory.getLogger(UserController.class);

    @Autowired
    AuthController authController;

    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }


    @PostMapping("/register")
    public ResponseEntity<User> createUser(@RequestBody @Valid User user)throws CustomException{
        log.debug("Endpoint POST ./user/register reached");
        User returningUser =  userService.createUser(user);
        return ResponseEntity.status(201).body(returningUser);
    }

    @PatchMapping
    public ResponseEntity<Object> updateLoggedInUserProfile(@RequestBody Map<String,String> newUser) throws CustomException {
        log.debug("Endpoint PATCH ./user reached");
        var user = userService.updateUserById(loggedInUserId(), newUser);
        return ResponseEntity.ok(user);
    }

    // handles all the custom exceptions
    @ExceptionHandler(CustomException.class)
    public ResponseEntity<Object> handleCustomException( CustomException e){
        log.warn("Exception was thrown: {}", e.getMsg());
        return ResponseEntity.status(e.getStatus()).body(e.getMsg());
    }

    private UUID loggedInUserId() throws CustomException {
        // later we'll use the ID that's in the Token
        User authUser = authController.getAuthenticatedUser();
        if(authUser != null){
            return authUser.getUserId();
        }
        // TODO: Please check if is that what you want to do
        log.warn("Method 'loggedInUserId' returning null");
        return null;
    }
}
