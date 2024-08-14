package com.revature.controllers;


import com.revature.exceptions.CustomException;
import com.revature.models.User;
import com.revature.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/user")
@CrossOrigin
public class UserController {

    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
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

    private int loggedInUserId(){
        // later we'll use the ID that's in the Token
        return 1;
    }
}
