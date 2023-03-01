package com.dlowji.simple.util;

import com.dlowji.simple.exception.JwtTokenMalformedException;
import com.dlowji.simple.exception.JwtTokenMissingException;
import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtUtil {

    @Value("${jwt.secret}")
    private String jwtSecret;

    @Value("${jwt.token.validity}")
    private long tokenValidity;

    public Claims getClaims(final String token) {
        try {
            return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody();
        } catch (Exception e) {
            System.out.println(e.getMessage() + " => " + e);
        }
        return null;
    }

    public String generateToken(String id) {
        Claims claims = Jwts.claims().setSubject(id);
        long nowMills = System.currentTimeMillis();
        long expMills = nowMills + tokenValidity;
        Date exp = new Date(expMills);
        return Jwts.builder().setClaims(claims).setIssuedAt(new Date(nowMills)).setExpiration(exp)
                .signWith(SignatureAlgorithm.HS512, jwtSecret).compact();
    }

    public void validateToken(final String token) throws JwtTokenMalformedException, JwtTokenMissingException {
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token);
        } catch (SignatureException exception) {
            throw new JwtTokenMalformedException("Invalid JWT signature");
        } catch (MalformedJwtException exception) {
            throw new JwtTokenMalformedException("Invalid JWT token");
        } catch (ExpiredJwtException exception) {
            throw new JwtTokenMalformedException("Expired JWT token");
        } catch (UnsupportedJwtException exception) {
            throw new JwtTokenMalformedException("Unsupported JWT token");
        } catch (IllegalArgumentException exception) {
            throw new JwtTokenMissingException("JWT claims string is empty");
        }
    }
}
