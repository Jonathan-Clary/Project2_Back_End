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
public class UserAuthService {

    @Autowired
    AuthenticationManager authManager;

    @Autowired
    UserDAO userDAO;

    @Autowired
    JwtTokenProvider jwtProvider;

    public OutgoingJwtUserDTO login(IncomingUserDTO userDTO) throws UserNotFoundException {

        String email =  userDTO.getEmail();
        String password =  userDTO.getPassword();

        // unauthenticated object
        Authentication unAuth = new UsernamePasswordAuthenticationToken(email,password);

        // authenticated object
        Authentication auth = null;
        try {
            auth = authManager.authenticate(unAuth);
        }
        catch (Exception e){
            System.out.println("Authentication Exception: "+e.getMessage());
        }
        if(auth != null && auth.isAuthenticated()){
            User authuser = userDAO.findByEmail(userDTO.getEmail()).get();

            String token = jwtProvider.generateToken(authuser.getUserId());

            // if authentication was successful, return a DTO with token, userId, and email
            return new OutgoingJwtUserDTO(token,authuser.getUserId(),authuser.getEmail());

        }else{
            // if login was unsuccessful, throw an exception
            throw new UserNotFoundException(userDTO.getEmail());
        }
    }



}
