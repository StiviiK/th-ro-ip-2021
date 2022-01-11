package de.throsenheim.ip.spm.util;

import de.throsenheim.ip.spm.models.UserPrincipal;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Date;
import java.util.Map;
import java.util.UUID;
import java.util.function.Function;

/**
 * Helper class which gives us easy methods to interact with JWTs.
 * Including generation, verification, data extraction
 *
 * @author Stefan Kürzeder
 */
@Configuration
public class JWTUtil {
    private final String SECRET_KEY = UUID.randomUUID().toString();

    /**
     * Extracts the username from the given token.
     * @param token The token to extract data from.
     * @return The extracted data.
     */
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    /**
     * Extracts the expiration from the given token.
     * @param token The token to extract data from.
     * @return The extracted data.
     */
    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    /**
     * Extracts all claims from the given token.
     * @param token The token to extract data from.
     * @return The extracted data.
     */
    private Claims extractAllClaims(String token) {
        return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
    }

    /**
     * Internal check to test if the token is expired.
     * @param token The token to test.
     * @return A boolean indicating if the token is expired.
     */
    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    /**
     * Generates a Token for the given user and the claims.
     * @param userDetails The user to generate the token for.
     * @param claims The claims to set in the token.
     * @return The signed token.
     */
    public String generateToken(UserPrincipal userDetails, Map<String, Object> claims) {
        return createToken(claims, userDetails);
    }

    /**
     * Internal: Generates a Token for the given user and the claims.
     * @param claims The claims to set in the token.
     * @param subject The user to generate the token for.
     * @return The signed token.
     */
    private String createToken(Map<String, Object> claims, UserPrincipal subject) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setNotBefore(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10)) // 10h
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY).compact();
    }

    /**
     * Validates if the Token is valid for the given user.
     * @param token The token to check.
     * @param userDetails The user to check for.
     * @return A boolean indicating the validity.
     */
    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }
}
