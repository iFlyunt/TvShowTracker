package com.tvshowtracker.security.validator;

import org.springframework.security.core.AuthenticationException;

public interface CredentialsValidator {
    String validateFirstName(String firstName) throws AuthenticationException;

    String validateLastName(String lastName) throws AuthenticationException;

    String validateUsername(String username) throws AuthenticationException;

    String validatePassword(String password) throws AuthenticationException;
}
