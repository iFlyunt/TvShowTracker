package com.ippt.server.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ippt.server.model.response.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Serializable;

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response, AuthenticationException e)
            throws IOException, ServletException {
        ObjectMapper objectMapper = new ObjectMapper();
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        objectMapper.writeValue(response.getOutputStream(), new ErrorResponse("Unauthorized"));

    }
}
