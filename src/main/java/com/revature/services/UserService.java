package com.revature.services;

import com.revature.DAOs.UserDAO;
import com.revature.exceptions.*;
import com.revature.models.User;
import jakarta.validation.Validation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Pattern;

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

    public User getUserByEmail(String email) throws CustomException {
        var user = userDAO.findByEmail(email);
        if(user.isEmpty())
            throw new UserNotFoundException(email);
        return user.get();
    public User createUser(User user) throws EmailAlreadyExistException, InvalidEmailFormatException, InvalidPasswordException {
        Optional<User> presentUser = userDAO.findByEmail(user.getEmail());
        String email = user.getEmail();
        String emailRegexPattern = "^(.+)@(\\S+)$";//Must be in email format
        String abideUpperCase = "(?=.*[A-Z])";//The string must contain at least 1 uppercase alphabetical character
        String abideNumericChar = "(?=.*[0-9])"; //The string must contain at least 1 numeric character
        String abideSpecChar = "(?=.[!@#\\$%\\^&])";//The string must contain at least one special character, but we are escaping reserved RegEx characters to avoid conflict

        if(presentUser.isPresent())//User already exists
            throw new EmailAlreadyExistException(user.getEmail());
        if(!user.getEmail().matches(emailRegexPattern))//Email abides to email pattern
            throw new InvalidEmailFormatException();
        if(!user.getEmail().matches(abideUpperCase))
            throw new InvalidPasswordException("Must contain at least 1 uppercase character");
        if(!user.getEmail().matches(abideNumericChar))
            throw new InvalidPasswordException("Must contain at least 1 numeric character");
        if(!user.getEmail().matches(abideSpecChar))
            throw new InvalidPasswordException("Must contain at least one special character(!@#\\$%\\^&)");



        return userDAO.save(user);
    }

    public User updateUserById(int userId, Map<String,String> newUser) throws CustomException {
        User user = getUserById(userId);

        if(newUser.containsKey("firstName"))
            user.setFirstName(newUser.get("firstName"));
        if(newUser.containsKey("lastName"))
            user.setLastName(newUser.get("lastName"));
        if(newUser.containsKey("email"))
            user.setEmail(newUser.get("email"));
        if(newUser.containsKey("password"))
            user.setFirstName(newUser.get("password"));

        // validate all fields
        var exception = validate(user);
        if(exception != null)
            throw exception;

        // check that the email doesn't already exist
        var user2 = userDAO.findByEmail(user.getEmail());
        if(user2.isPresent())
            if(user2.get().getUserId() != userId)
                throw new EmailAlreadyExistException(user.getEmail());

        return userDAO.save(user);
    }

    private CustomException validate(User user){
        try(var validator = Validation.buildDefaultValidatorFactory()){
            var errs = validator.getValidator().validate(user);
            if(!errs.isEmpty()){
                var exception = new InvalidUserException();//        field name          ,   error message
                errs.forEach(err -> exception.addMessage(err.getPropertyPath().toString(), err.getMessage()));
                return exception;
            }
        }
        return null;
    }
}
