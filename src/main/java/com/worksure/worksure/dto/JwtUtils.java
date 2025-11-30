package com.worksure.worksure.dto;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;

@Component("jwtDtoUtils")
public class JwtUtils {
    private final String SECRET = "4/pH9Gl7YRJVrZoPovUNRgE3LS/3Ns1EgQcN+S98yPI9VAXUIUVk/eX5/ZlKoLB3";

    public String getUsernameFromToken(String token) {
        return Jwts.parser()
                   .setSigningKey(SECRET)
                   .parseClaimsJws(token)
                   .getBody()
                   .getSubject();
    }
}
