package com.ippt.controller;

import com.ippt.entity.Subscriber;
import com.ippt.model.request.LoginRequest;
import com.ippt.model.request.RegistrationRequest;
import com.ippt.model.response.ErrorResponse;
import com.ippt.model.response.InfoResponse;
import com.ippt.model.response.TokenResponse;
import com.ippt.repository.UserRepository;
import com.ippt.security.util.JwtTokenUtil;
import com.ippt.security.validator.CredentialsValidator;
import com.ippt.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController()
@RequestMapping("/auth")
public class UserAuthenticationController {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserServiceImpl userService;

    @Autowired
    @Qualifier("customUserDetailService")
    private UserDetailsService userSecurityService;

    @Autowired
    private CredentialsValidator credentialsValidator;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        UserDetails user = userSecurityService.loadUserByUsername(loginRequest.getUsername());
        boolean passwordsMatched = passwordEncoder.matches(loginRequest.getPassword(),
                                                           user.getPassword());
        if (!passwordsMatched)
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                                 .body(new ErrorResponse("Incorrect password"));
        authenticate(loginRequest);
        final String token = JwtTokenUtil.generateToken(user);
        return ResponseEntity.status(HttpStatus.OK).body(new TokenResponse(token));
    }

    @PostMapping("/sign-up")
    public ResponseEntity<?> signUp(@RequestBody RegistrationRequest registrationRequest) {
        try {
            validateCredentials(registrationRequest);
        } catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                                 .body(new ErrorResponse(e.getLocalizedMessage()));
        }
        if (isUserAlreadyExists(registrationRequest.getUsername()))
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                                        .body(new ErrorResponse("User with such name"
                                                                + " is already registered"));
        userService.addNewUser(registrationRequest);
        return ResponseEntity.status(HttpStatus.OK)
                             .body(new InfoResponse("You've been successfully registered"));
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<?> handleAuthenticationException(AuthenticationException e) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                             .body(new ErrorResponse(e.getLocalizedMessage()));
    }

    private void authenticate(LoginRequest loginRequest) {
        final UsernamePasswordAuthenticationToken authentication;
        authentication = new UsernamePasswordAuthenticationToken(loginRequest.getUsername(),
                                                                 loginRequest.getPassword());
        authenticationManager.authenticate(authentication);
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    private void validateCredentials(RegistrationRequest registrationRequest)
            throws AuthenticationException {
        credentialsValidator.validateFirstName(registrationRequest.getFirstName());
        credentialsValidator.validateLastName(registrationRequest.getLastName());
        credentialsValidator.validateUsername(registrationRequest.getUsername());
        credentialsValidator.validatePassword(registrationRequest.getPassword());
    }

    private boolean isUserAlreadyExists(String username) {
        Subscriber user = userService.findUserByUsername(username);
        return user != null;
    }
}
