package br.app.fsantana.marketspaceapi.secutiry.services.impl;

import br.app.fsantana.marketspaceapi.domain.models.User;
import br.app.fsantana.marketspaceapi.secutiry.config.SecurityProperties;
import br.app.fsantana.marketspaceapi.secutiry.services.TokenService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.time.OffsetDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * Created by felip on 12/10/2025.
 */
@Log4j2
@Service
@RequiredArgsConstructor
public class TokenServiceImpl implements TokenService {


    private final SecurityProperties properties;

    @Override
    public String extractUserId(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    @Override
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    @Override
    public String generateToken(User userDetails) {
        return generateToken(new HashMap<>(), userDetails);
    }

    @Override
    public String generateToken(Map<String, Object> extraClaims, User userDetails) {
        return buildToken(extraClaims, userDetails, getExpirationTime() );
    }

    @Override
    public long getExpirationTime() {
        return properties.getToken().getExpirationTime();
    }

    @Override
    public boolean isTokenValid(String token, User userDetails) {
        final String userId = extractUserId(token);
        return (userId.equals(userDetails.getId().toString())) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    private Claims extractAllClaims(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(properties.getToken().getSecret());
        return Keys.hmacShaKeyFor(keyBytes);
    }

    private String buildToken(
            Map<String, Object> extraClaims,
            User userDetails,
            long expiration
    ) {
        Date date = Date.from(OffsetDateTime.now().toInstant());
        return Jwts
                .builder()
                .setClaims(extraClaims)
                .setSubject(userDetails.getId().toString())
                .setIssuedAt(Date.from(OffsetDateTime.now().toInstant()))
                .setExpiration( Date.from(OffsetDateTime.now().plusMinutes(expiration).toInstant()) )
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }
}
