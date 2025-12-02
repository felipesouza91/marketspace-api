package br.app.fsantana.marketspaceapi.secutiry.config;

import br.app.fsantana.marketspaceapi.domain.dataprovider.UserDataProvider;
import br.app.fsantana.marketspaceapi.domain.models.User;
import br.app.fsantana.marketspaceapi.secutiry.models.Auth;
import br.app.fsantana.marketspaceapi.secutiry.services.TokenService;
import br.app.fsantana.marketspaceapi.domain.exceptions.AppSecurityException;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.io.IOException;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final HandlerExceptionResolver handlerExceptionResolver;
    private final TokenService jwtService;
    private final UserDetailsService userDetailsService;
    private final UserDataProvider userDataProvider;

    @Override
    protected void doFilterInternal(
        @NonNull HttpServletRequest request,
        @NonNull HttpServletResponse response,
        @NonNull FilterChain filterChain
    ) throws ServletException, IOException {

        try {
            final String authHeader = request.getHeader("Authorization");

            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                filterChain.doFilter(request, response);
                return;
            }
            final String jwt = authHeader.substring(7);
            final String userId = jwtService.extractUserId(jwt);

            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

            if (userId != null && authentication == null) {
                User userData = userDataProvider.findById(UUID.fromString(userId))
                        .orElseThrow();
                Auth userDetails = (Auth) this.userDetailsService.loadUserByUsername(userData.getEmail());
                boolean result = jwtService.isTokenValid(jwt, userDetails.getUser());
                if (result) {
                    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                            userDetails,
                            null,
                            userDetails.getAuthorities()
                    );
                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                }
            }
            filterChain.doFilter(request, response);
        }  catch (ExpiredJwtException e) {
            handlerExceptionResolver.resolveException(request, response, null, new AppSecurityException("Token invalid"));
        } catch (Exception exception) {
            logger.error(exception);
            handlerExceptionResolver.resolveException(request, response, null, exception);
        }
    }
}