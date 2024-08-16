package com.revature.controllers;

import com.revature.DTOs.IngoingUserDTO;
import com.revature.DTOs.OutgoingJwtUserDTO;
import com.revature.exceptions.CustomException;
import com.revature.exceptions.UserNotFoundException;
import com.revature.models.User;
import com.revature.services.UserAuthService;
import com.revature.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class UserAuthController {

    @Autowired
    UserAuthService userAuthService;

    @Autowired
    UserService userService;

    @PostMapping("/login")
    public ResponseEntity<OutgoingJwtUserDTO> auth(@RequestBody IngoingUserDTO loginDTO) throws UserNotFoundException {
        OutgoingJwtUserDTO jwtUserDTO = userAuthService.login(loginDTO);
        return ResponseEntity.status(201).body(jwtUserDTO);
    }

    public User getAuthenticatedUser() throws CustomException {
        // Get the authentication object
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // Check if authentication is not null and if the user is authenticated
        if (authentication != null && authentication.isAuthenticated()) {

            // Since it's a JWT token-based auth
            // the principal(user/username) will be a string
            // Get the username(email) of the authenticated user
            String email =  authentication.getPrincipal().toString();

            return userService.getUserByEmail(email);
        }
        return null;
    }


    // handles all the custom exceptions
    @ExceptionHandler(CustomException.class)
    public ResponseEntity<Object> handleCustomException( CustomException e){
        return ResponseEntity.status(e.getStatus()).body(e.getMsg());
    }



}
