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
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {

        User user = userDAO.findById(Integer.parseInt(userId)).orElseThrow(()->{
            throw new UsernameNotFoundException(userId);
        });

        return new UserDetailsImpl(user);
    }
}
