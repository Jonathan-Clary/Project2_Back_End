package com.revature.services;

import com.revature.DAOs.UserDAO;
import com.revature.DTOs.IncomingUserDTO;
import com.revature.DTOs.OutgoingJwtUserDTO;
import com.revature.exceptions.UserNotFoundException;
import com.revature.models.User;
import com.revature.security.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    // Inject the user-specific authentication manager
    @Autowired
    AuthenticationManager authManager;

    // Inject the Data Access Object for the User entity
    @Autowired
    UserDAO userDAO;

    // Inject the JWT provider for generating tokens
    @Autowired
    JwtTokenProvider jwtProvider;

    // Method to handle login for users
    public OutgoingJwtUserDTO login(IncomingUserDTO userDTO) throws UserNotFoundException {

        // Extract email from the incoming DTO
        String email = userDTO.getEmail();

        // Extract password from the incoming DTO
        String password = userDTO.getPassword();

        // Create an unauthenticated authentication object using email and password
        Authentication unAuth = new UsernamePasswordAuthenticationToken(email, password);

        // Initialize an authenticated object to null
        Authentication auth = null;
        try {
            // Authenticate the unauthenticated object using the authManager
            auth = authManager.authenticate(unAuth);
        } catch (Exception e) {
            // Print authentication exception message in case of failure
            System.out.println("Authentication Exception: " + e.getMessage());
        }

        // Check if authentication was successful and the user is authenticated
        if (auth != null && auth.isAuthenticated()) {
            // Find the authenticated user in the database using the email
            User authuser = userDAO.findByEmail(userDTO.getEmail()).get();

            // Generate a JWT token for the authenticated user
            String token = jwtProvider.generateToken(authuser.getUserId());

            // If authentication was successful, return a DTO with token, userId, and email
            return new OutgoingJwtUserDTO(token, authuser.getUserId(), authuser.getEmail());

        } else {
            // If login was unsuccessful, throw a UserNotFoundException with the provided email
            throw new UserNotFoundException().withEmail(userDTO.getEmail());
        }
    }
}
