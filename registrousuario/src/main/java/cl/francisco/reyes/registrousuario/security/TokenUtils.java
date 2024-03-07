package cl.francisco.reyes.registrousuario.security;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.SecretKey;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.lang.Collections;
import io.jsonwebtoken.security.Keys;

public class TokenUtils {

    private final static String ACCESS_TOKEN_SECRET = "210d3d27605d0ee737952294265400a7cba109746afacd84b9cb0139bf6b7e3a";
    private final static Long ACCESS_TOKEN_VALID_SECONDS = 2_592_000L;

    public static String createToken(String nombre, String email) {
        
        long expirationTime = ACCESS_TOKEN_VALID_SECONDS * 1000;
        Date expirationDate = new Date(System.currentTimeMillis() + expirationTime);

        Map<String, Object> extra = new HashMap<>();
        extra.put("nombre", nombre);

        return Jwts.builder()
                .subject(email)
                .expiration(expirationDate)
                .claims(extra)
                .signWith(Keys.hmacShaKeyFor(ACCESS_TOKEN_SECRET.getBytes()))
                .compact();

    }

    public static UsernamePasswordAuthenticationToken getAuthentication(String token) {
        try{
            SecretKey key = Keys.hmacShaKeyFor(ACCESS_TOKEN_SECRET.getBytes());
            JwtParser parser = Jwts.parser()
                                    .verifyWith(key)
                                    .build();
            Claims claims = parser.parseSignedClaims(token).getPayload();

            String email = claims.getSubject();
            return new UsernamePasswordAuthenticationToken(email, null, Collections.emptyList());
        } catch (JwtException e){
            return null;
        }
    }
}