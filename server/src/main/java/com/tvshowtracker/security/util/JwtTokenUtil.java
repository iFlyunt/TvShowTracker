package com.tvshowtracker.security.util;

import com.tvshowtracker.security.JwtTokenConstants;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

public final class JwtTokenUtil {
    public static String getUsernameFromToken(String token) {
        return Jwts.parser().setSigningKey(JwtTokenConstants.SECRET).parseClaimsJws(token).getBody()
                   .getSubject();
    }

    public static Boolean validateToken(String token, UserDetails userDetails) {
        User user = (User) userDetails;
        final String username = getUsernameFromToken(token);
        return username.equals(user.getUsername());
    }

    public static String generateToken(UserDetails userDetails) {
        return Jwts.builder().setSubject(userDetails.getUsername())
                   .signWith(SignatureAlgorithm.HS512, JwtTokenConstants.SECRET)
                   .compact();
    }
}
