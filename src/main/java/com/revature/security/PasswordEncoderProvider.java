package com.revature.security;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class PasswordEncoderProvider extends BCryptPasswordEncoder {
    public PasswordEncoderProvider() {
        super(12);
    }
}
