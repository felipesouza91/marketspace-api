package br.app.fsantana.marketspaceapi.secutiry.api.controller;

import br.app.fsantana.marketspaceapi.domain.models.User;
import br.app.fsantana.marketspaceapi.secutiry.api.request.AuthRequest;
import br.app.fsantana.marketspaceapi.secutiry.api.request.RefreshTokenRequest;
import br.app.fsantana.marketspaceapi.secutiry.api.request.UserCreateRequest;
import br.app.fsantana.marketspaceapi.secutiry.api.response.AuthResponse;
import br.app.fsantana.marketspaceapi.secutiry.api.response.TokenResponse;
import br.app.fsantana.marketspaceapi.secutiry.models.Auth;
import br.app.fsantana.marketspaceapi.secutiry.services.SessionService;
import br.app.fsantana.marketspaceapi.utils.exceptions.AppRuleException;
import br.app.fsantana.marketspaceapi.utils.mappers.SessionMapper;
import br.app.fsantana.marketspaceapi.utils.mappers.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by felip on 12/10/2025.
 */

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class SessionController {

    private final SessionService sessionService;
    private final UserMapper userMapper;
    private final SessionMapper sessionMapper;

    @PostMapping("/signup")
    public ResponseEntity<?> createUser(@RequestBody UserCreateRequest request) {
        try {
            User user = userMapper.toModel(request);
            sessionService.createUser(user);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (AppRuleException e ) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e ) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/token")
    public ResponseEntity<AuthResponse> authentication(@RequestBody AuthRequest request) {
        Auth data = sessionService.auth(request.getEmail(), request.getPassword());
        return ResponseEntity.ok(sessionMapper.toResponse(data));
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<TokenResponse> refreshToken(@RequestBody RefreshTokenRequest request) {
        Auth data = sessionService.refreshToken(request.getRefreshToken());
        return ResponseEntity.ok(sessionMapper.toTokenResponse(data));
    }
}
