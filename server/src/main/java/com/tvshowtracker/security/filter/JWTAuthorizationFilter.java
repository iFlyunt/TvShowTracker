package com.tvshowtracker.security.filter;

import com.tvshowtracker.security.JwtTokenConstants;
import com.tvshowtracker.security.util.JwtTokenUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JWTAuthorizationFilter extends OncePerRequestFilter {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private String      requestHeader;
    private String      username;
    private String      authToken;
    private UserDetails userDetails;

    @Autowired
    @Qualifier("customUserDetailService")
    private UserDetailsService userSecurityService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain chain) throws IOException, ServletException {
        requestHeader = request.getHeader(JwtTokenConstants.HEADER);
        parseAuthToken();
        try {
            username = JwtTokenUtil.getUsernameFromToken(authToken);
        } catch (IllegalArgumentException e) {
            logger.error("an error occured during getting username from token", e);
        }
        logger.debug("checking authentication for user '{}'", username);
        if (isUserNonNullAndUnauthorized()) {
            userDetails = userSecurityService.loadUserByUsername(username);
            if (isTokenValid())
                startAuthorization(request);
        }
        chain.doFilter(request, response);
    }

    private void parseAuthToken() {
        if (isHeaderValidAndNonNull())
            authToken = requestHeader.replace(JwtTokenConstants.BEARER_PREFIX, "");
        else {
            logger.warn("couldn't find bearer string, will ignore the header");
            authToken = null;
        }
    }

    private void startAuthorization(HttpServletRequest request) {
        logger.debug("security context was null, start authorizing user");
        UsernamePasswordAuthenticationToken authentication;
        authentication = new UsernamePasswordAuthenticationToken(userDetails,
                                                                 null,
                                                                 userDetails.getAuthorities());
        authentication.setDetails(new WebAuthenticationDetailsSource()
                                          .buildDetails(request));
        logger.info("authorized user '{}', setting security context", username);
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    private boolean isUserNonNullAndUnauthorized() {
        return username != null && SecurityContextHolder.getContext().getAuthentication() == null;
    }

    private boolean isTokenValid() {
        return JwtTokenUtil.validateToken(authToken, userDetails);
    }

    private boolean isHeaderValidAndNonNull() {
        return requestHeader != null && requestHeader.startsWith(JwtTokenConstants.BEARER_PREFIX);
    }
}
