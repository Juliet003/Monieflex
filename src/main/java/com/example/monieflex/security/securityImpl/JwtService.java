package com.example.monieflex.security.securityImpl;

import com.example.monieflex.enums.Roles;
import com.example.monieflex.exceptions.JwtParsingException;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
@Component
@Slf4j
public class JwtService {
    private static final String secretToken = "sdfghjklsdfghjksdfghjdfghjertyucvbertyxcvertyxcvtyvertyertyuertyuxcvxcertertdfgxcvsdfgdf";

    private Key getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretToken);
        return Keys.hmacShaKeyFor(keyBytes);
    }
    public String generateToken(Authentication authentication, Roles role) {
        UserDetailsImpl userDetails = ( UserDetailsImpl) authentication.getPrincipal();
       String email = userDetails.getEmail();
       String fullName = userDetails.getFullName();

        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + 60000 * 120);

        return Jwts.builder()
                .setSubject(email)
                .claim("name", fullName)
                .claim("email", email)
                .claim("role", role.name())
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(getSigningKey(), SignatureAlgorithm.HS512)
                .compact();
    }
    public Map<String, String> parseTokenClaims(String token) throws JwtParsingException {
        try {
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(getSigningKey())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();

            return new HashMap<>() {{
                put("name", claims.get("name", String.class));
                put("email", claims.get("email", String.class));
                put("role", claims.get("role", String.class));
            }};
        } catch (ExpiredJwtException | UnsupportedJwtException | MalformedJwtException | SignatureException |
                 IllegalArgumentException e) {
            throw new JwtParsingException("Error parsing JWT token: " + e.getMessage());
        }
    }
    public boolean validateToken(String token, UserDetails userDetails){
        try {
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(getSigningKey())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();

            String tokenEmail = claims.get("email",String.class);
            Date expiration = claims.getExpiration();

            return (tokenEmail != null && tokenEmail.equals(userDetails.getUsername()) && !isTokenExpired(expiration));
        }catch (ExpiredJwtException e){
            log.error("JWT Token has Expired : {} ",e.getMessage());
        }catch (UnsupportedJwtException e){
            log.error("JWT Token is unsupported : {} ",e.getMessage());
        }catch (MalformedJwtException e){
            log.error("JWT Token is malformed : {} ",e.getMessage());
        }catch (SignatureException e){
            log.error("Invalid JWT Signature: {}",e.getMessage());
        }catch(IllegalArgumentException e){
            log.error("JWT Token claims String is empty: {}",e.getMessage());
        }
        return false;
    }

    private boolean isTokenExpired(Date expiration) {
        return expiration.before(new Date());
    }
}
