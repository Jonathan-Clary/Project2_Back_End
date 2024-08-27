package com.revature.controllers;

import com.revature.DTOs.IncomingUserDTO;
import com.revature.DTOs.OutgoingJwtUserDTO;
import com.revature.exceptions.CustomException;
import com.revature.exceptions.UserNotAuthenticatedException;
import com.revature.exceptions.UserNotFoundException;
import com.revature.models.User;
import com.revature.services.AuthService;
import com.revature.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@CrossOrigin
public class AuthController {

    @Autowired
    AuthService authService;

    @Autowired
    UserService userService;

    @PostMapping("/login")
    public ResponseEntity<OutgoingJwtUserDTO> auth(@RequestBody IncomingUserDTO loginDTO) throws UserNotFoundException {
        OutgoingJwtUserDTO jwtUserDTO = authService.login(loginDTO);
        return ResponseEntity.status(201).body(jwtUserDTO);
    }

    public User getAuthenticatedUser() throws UserNotFoundException, UserNotAuthenticatedException {
        return authService.getLoggedInUser();
    }


    // handles all the custom exceptions
    @ExceptionHandler(CustomException.class)
    public ResponseEntity<Object> handleCustomException( CustomException e){
        return ResponseEntity.status(e.getStatus()).body(e.getMsg());
    }



}
