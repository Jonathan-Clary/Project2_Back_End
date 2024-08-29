package com.revature.security;

import com.revature.DAOs.UserDAO;
import com.revature.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    // Inject the UserDAO to interact with the database
    @Autowired
    public UserDAO userDAO;

    // Load user details by username (email in this case)
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        // Find the user by email (username) or throw an exception if not found
        User user = userDAO.findByEmail(username).orElseThrow(() -> {
            return new UsernameNotFoundException("No user with username " + username);
        });

        // Return a UserDetailsImpl object containing the user's details
        return new UserDetailsImpl(user);
    }

    // Load user details by user ID
    public UserDetails loadUserByUserId(UUID userId) throws UsernameNotFoundException {

        // Find the user by ID or throw an exception if not found
        User user = userDAO.findById(userId).orElseThrow(() -> {
            return new UsernameNotFoundException("User with user ID: " + userId);
        });

        // Return a UserDetailsImpl object containing the user's details
        return new UserDetailsImpl(user);
    }
}
