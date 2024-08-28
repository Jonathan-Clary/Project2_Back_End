package com.revature.services;

import com.revature.DAOs.UserDAO;
import com.revature.DTOs.IncomingUserDTO;
import com.revature.DTOs.OutgoingJwtUserDTO;
import com.revature.exceptions.UserNotAuthenticatedException;
import com.revature.exceptions.UserNotFoundException;
import com.revature.models.User;
import com.revature.security.JwtTokenProvider;
import com.revature.security.UserDetailsImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    // Inject the user-specific authentication manager
    @Autowired
    AuthenticationManager authManager;
    Logger log = LoggerFactory.getLogger(AuthService.class);
    // Inject the Data Access Object for the User entity
    @Autowired
    UserDAO userDAO;

    // Inject the JWT provider for generating tokens
    @Autowired
    JwtTokenProvider jwtProvider;

    @Autowired
    UserService userService;

    // Method to handle login for users
    public OutgoingJwtUserDTO login(IncomingUserDTO userDTO) throws UserNotFoundException {
        log.debug("Method 'login' invoked with userDTO: {}", userDTO);
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
            log.warn("Method 'login' Authentication Exception Thrown: {}", e.getMessage(),e);
            System.out.println("Authentication Exception: " + e.getMessage());
        }

        // Check if authentication was successful and the user is authenticated
        if (auth != null && auth.isAuthenticated()) {
            // Find the authenticated user in the database using the email
            User authuser = userDAO.findByEmail(userDTO.getEmail()).get();

            // Generate a JWT token for the authenticated user
            String token = jwtProvider.generateToken(authuser);

            // If authentication was successful, return a DTO with token, userId, and email
            OutgoingJwtUserDTO outgoingJwtUserDTO = new OutgoingJwtUserDTO(token, authuser.getUserId(), authuser.getEmail());
            log.debug("Method 'login' returning {}", outgoingJwtUserDTO);
            return outgoingJwtUserDTO;

        } else {
            // If login was unsuccessful, throw a UserNotFoundException with the provided email
            throw new UserNotFoundException().withEmail(userDTO.getEmail());
        }
    }

    public User getLoggedInUser() throws UserNotFoundException, UserNotAuthenticatedException{
        log.debug("Method 'getLoggedInUser' invoked");
        // Get the authentication object
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // Check if authentication is not null and if the user is authenticated
        if (authentication != null && authentication.isAuthenticated()) {

            // Since it's a JWT token-based auth
            // the principal(user/username) will be a string
            // Get the username(email) of the authenticated user
            UserDetailsImpl userDetails =  (UserDetailsImpl) authentication.getPrincipal();
            String email = userDetails.getUsername();

            User user = userService.getUserByEmail(email);
            log.debug("Method 'getLoggedInUser' returning: {}", user);
            return user;
        }
        else{
            throw new UserNotAuthenticatedException();
        }
    }

}
