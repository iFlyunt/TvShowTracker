package com.ippt.server.security.validator;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

@Component
public class CredentialsValidatorImpl implements CredentialsValidator {
    private static final NumberRange FIRSTNAME_LENGTH = new NumberRange(1, 60);
    private static final NumberRange LASTNAME_LENGTH  = new NumberRange(1, 60);
    private static final NumberRange USERNAME_LENGTH  = new NumberRange(2, Integer.MAX_VALUE);

    private static final String passwordPattern = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[$@$!%*#?&])[A-Za-z\\d$@$!%*#?&]{6,}$";

    @Override
    public String validateFirstName(String firstName) throws AuthenticationException {
        int length = firstName.length();
        if (!FIRSTNAME_LENGTH.contains(length))
            throw new BadCredentialsException("Invalid first name length: " + length);
        return firstName;
    }

    @Override
    public String validateLastName(String lastName) throws AuthenticationException {
        int length = lastName.length();
        if (!LASTNAME_LENGTH.contains(length))
            throw new BadCredentialsException("Invalid last name length: " + length);
        return lastName;
    }

    @Override
    public String validateUsername(String username) throws AuthenticationException {
        int length = username.length();
        if(!USERNAME_LENGTH.contains(length))
            throw new BadCredentialsException("Invalid username length: " + length);
        return username;
    }

    @Override
    public String validatePassword(String password) throws AuthenticationException {
        if (!password.matches(passwordPattern))
            throw new BadCredentialsException("Invalid password encountered");
        return password;
    }
}
