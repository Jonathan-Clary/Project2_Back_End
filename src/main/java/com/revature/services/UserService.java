package com.revature.services;

import com.revature.DAOs.UserDAO;
import com.revature.exceptions.CustomException;
import com.revature.exceptions.InvalidIDException;
import com.revature.exceptions.UserNotFoundException;
import com.revature.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class UserService {

    private UserDAO userDAO;

    @Autowired
    public UserService(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    public User getUserById(int userId) throws CustomException {
        if(userId <= 0)
            throw new InvalidIDException();
        var user = userDAO.findById(userId);
        if(user.isEmpty())
            throw new UserNotFoundException(userId);
        return user.get();
    }


}
