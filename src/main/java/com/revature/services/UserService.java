package com.revature.services;

import com.revature.DAOs.UserDAO;
import com.revature.exceptions.*;
import com.revature.models.User;
import com.revature.security.PasswordEncoderProvider;
import jakarta.validation.Validation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.regex.Pattern;

@Service
public class UserService {

    Logger log = LoggerFactory.getLogger(UserService.class);

    private UserDAO userDAO;

    @Autowired
    EmailService emailService;

    // Encodes the password before saving it to the database
    @Autowired
    private PasswordEncoderProvider passwordEncoder;

    @Autowired
    public UserService(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    public User getUserById(UUID userId) throws CustomException {
        log.debug("Method 'getUserById' invoked with userId: {}", userId);
        if(userId == null)
            throw new InvalidIDException();
        var user = userDAO.findById(userId);
        if(user.isEmpty())
            throw new UserNotFoundException(userId);
        log.debug("Method 'getUserById' returning: {}", user.get().toString());
        return user.get();
    }


    public User getUserByEmail(String email) throws UserNotFoundException {
        log.debug("Method 'getUserByEmail' invoked with email: {}", email);
        var user = userDAO.findByEmail(email);
        if (user.isEmpty())
            throw new UserNotFoundException().withEmail(email);

        log.debug("Method 'getUserByEmail' returning: {}", user.get().toString());
        return user.get();
    }


    public User createUser(User user) throws CustomException {
        log.debug("Method 'createUser' invoked with user: {}", user.toString());

        Optional<User> presentUser = userDAO.findByEmail(user.getEmail());
        String email = user.getEmail();
        String emailRegexPattern = "^(.+)@(\\S+)$";//Must be in email format
        String abideUpperCase = "(?=.*[A-Z])";//The string must contain at least 1 uppercase alphabetical character
        String abideNumericChar = "(?=.*[0-9])"; //The string must contain at least 1 numeric character
        String abideSpecChar = "(?=.[!@#\\$%\\^&])";//The string must contain at least one special character, but we are escaping reserved RegEx characters to avoid conflict

        if(presentUser.isPresent())//User already exists
            throw new EmailAlreadyExistException(user.getEmail());

        // TODO: Commented for now. To be tested separately
        /*
        if(!user.getEmail().matches(emailRegexPattern))//Email abides to email pattern
            throw new InvalidEmailFormatException();
        if(!user.getPassword().matches(abideUpperCase))
            throw new InvalidPasswordException("Must contain at least 1 uppercase character");
        if(!user.getPassword().matches(abideNumericChar))
            throw new InvalidPasswordException("Must contain at least 1 numeric character");
        if(!user.getPassword().matches(abideSpecChar))
            throw new InvalidPasswordException("Must contain at least one special character(!@#\\$%\\^&)");
        */

        // Encodes the password before saving it to the database
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);

        emailService.sendVerificationEmail(email);

        User returningUser = userDAO.save(user);
        log.debug("Method 'createUser' returning: {}", returningUser);

        return returningUser;
    }

    public User updateUserById(UUID userId, Map<String,String> newUser) throws CustomException {
        log.debug("Method 'updateUserById' invoked with userId: {}, newUser: {}", userId,newUser.toString());
        User user = getUserById(userId);

        if(newUser.containsKey("firstName")) {
            user.setFirstName(newUser.get("firstName"));
        }
        if(newUser.containsKey("lastName")) {
            user.setLastName(newUser.get("lastName"));
        }
        if(newUser.containsKey("email")) {
            emailService.sendVerificationEmail(newUser.get("email"));
            user.setEmail(newUser.get("email"));
        }
        if(newUser.containsKey("password")){
            String encodedPassword = passwordEncoder.encode(newUser.get("password"));
            user.setPassword(encodedPassword);
        }

        // validate all fields
        var exception = validate(user);
        if(exception != null)
            throw exception;

        // check that the email doesn't already exist
        var user2 = userDAO.findByEmail(user.getEmail());
        if(user2.isPresent())
            if(user2.get().getUserId() != userId)
                throw new EmailAlreadyExistException(user.getEmail());

        User returningUser = userDAO.save(user);
        log.debug("Method 'updateUserById' returning: {}", returningUser.toString());
        return returningUser;
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
