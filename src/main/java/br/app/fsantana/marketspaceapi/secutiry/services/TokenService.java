package br.app.fsantana.marketspaceapi.secutiry.services;


import br.app.fsantana.marketspaceapi.domain.models.User;
import io.jsonwebtoken.Claims;

import java.util.Map;
import java.util.function.Function;

/**
 * Created by felip on 12/10/2025.
 */

public interface TokenService {

     String extractUserId(String token);

     <T> T extractClaim(String token, Function<Claims, T> claimsResolver) ;

     String generateToken(User userDetails);

     String generateToken(Map<String, Object> extraClaims, User userDetails) ;

     long getExpirationTime() ;

     boolean isTokenValid(String token, User userDetails) ;
}
