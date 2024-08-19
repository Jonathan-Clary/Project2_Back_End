package com.revature.security;

import com.revature.DAOs.UserDAO;
import com.revature.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    public UserDAO userDAO;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        //We'll be using the email as a username
        User user = userDAO.findByEmail(username).orElseThrow(()->{
            return new UsernameNotFoundException("No user with username "+username);
        });

        return new UserDetailsImpl(user);
    }


    public UserDetails loadUserByUserId(int userId) throws UsernameNotFoundException {

        User user = userDAO.findById(userId).orElseThrow(()->{
            return new UsernameNotFoundException("User with user ID: "+userId);
        });

        return new UserDetailsImpl(user);
    }
}
