package by.yatsukovich.security.jwt;

import by.yatsukovich.security.config.JWTConfiguration;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static io.jsonwebtoken.Claims.SUBJECT;
import static java.util.Calendar.MILLISECOND;

@Component
@RequiredArgsConstructor
public class TokenUtils {
    public static final String CREATE_VALUE = "created";

    public static final String ROLES = "roles";

    public static final String JWT = "JWT";

    public static final SignatureAlgorithm ALGORITHM = SignatureAlgorithm.HS512;

    private final JWTConfiguration jwtConfiguration;

    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        claims.put(SUBJECT, userDetails.getUsername());
        claims.put(CREATE_VALUE, generateCurrentDate());
        claims.put(ROLES, getEncryptedRoles(userDetails));

        return generateToken(claims);
    }

    public boolean validateToken(String token, UserDetails userDetails) {
        final String username = getUsernameFromToken(token);
        return username.equals(userDetails.getUsername());
    }

    public String getUsernameFromToken(String token) {
        return getClaimsFromToken(token).getSubject();
    }

    private Date generateCurrentDate() {
        return new Date();
    }

    private List<String> getEncryptedRoles(UserDetails userDetails) {

        return userDetails.getAuthorities().
                stream()
                .map(GrantedAuthority::getAuthority)
                .map(s -> s.replace("ROLE_", ""))
                .map(String::toLowerCase)
                .toList();
    }
    private String generateToken(Map<String, Object> claims) {

        return Jwts
                .builder()
                /*Set headers with algo and token type info*/
                .setHeader(generateJWTHeaders())
                /*We create payload with user info, roles, expiration date of token*/
                .setClaims(claims)
                .setExpiration(generateExpirationDate())
                /*Signature*/
                .signWith(ALGORITHM, jwtConfiguration.getSecret())
                .compact();
    }
    private Map<String, Object> generateJWTHeaders() {
        Map<String, Object> jwtHeaders = new LinkedHashMap<>();
        jwtHeaders.put("type", JWT);
        jwtHeaders.put("algorithm", ALGORITHM.getValue());

        return jwtHeaders;
    }

    private Date generateExpirationDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(MILLISECOND, jwtConfiguration.getExpiration());

        return calendar.getTime();
    }

    private Claims getClaimsFromToken(String token) {
        return Jwts
                .parser()
                .setSigningKey(jwtConfiguration.getSecret())
                .parseClaimsJws(token)
                .getBody();
    }

}
