package com.revature.controllers;

import com.revature.DTOs.IncomingUserDTO;
import com.revature.DTOs.OutgoingJwtUserDTO;
import com.revature.exceptions.CustomException;
import com.revature.exceptions.UserNotAuthenticatedException;
import com.revature.exceptions.UserNotFoundException;
import com.revature.models.User;
import com.revature.services.AdminService;
import com.revature.services.AuthService;
import com.revature.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
//TODO: !!!!!!
//TODO: !!!Need to change to url of s3!!!
@CrossOrigin(origins="http://travelplannerapp.s3-website-us-east-1.amazonaws.com:3000", allowCredentials = "true")
public class AuthController {
    Logger log = LoggerFactory.getLogger(AuthController.class);

    @Autowired
    AuthService authService;

    @Autowired
    UserService userService;

    @PostMapping("/login")
    public ResponseEntity<OutgoingJwtUserDTO> auth(@RequestBody IncomingUserDTO loginDTO) throws UserNotFoundException {
        log.debug("Endpoint POST ./auth/login reached");
        OutgoingJwtUserDTO jwtUserDTO = authService.login(loginDTO);
        log.info("User: {} logged in successfully",loginDTO.getEmail());
        return ResponseEntity.status(201).body(jwtUserDTO);
    }

    public User getAuthenticatedUser() throws UserNotFoundException, UserNotAuthenticatedException {
        return authService.getLoggedInUser();
    }


    // handles all the custom exceptions
    @ExceptionHandler(CustomException.class)
    public ResponseEntity<Object> handleCustomException( CustomException e){
        log.warn("Exception was thrown: {}", e.getMsg());
        return ResponseEntity.status(e.getStatus()).body(e.getMsg());
    }



}
