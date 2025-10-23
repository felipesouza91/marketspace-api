package br.app.fsantana.marketspaceapi.secutiry.services.impl;

import br.app.fsantana.marketspaceapi.domain.dataprovider.UserDataProvider;
import br.app.fsantana.marketspaceapi.domain.models.RefreshToken;
import br.app.fsantana.marketspaceapi.domain.models.User;
import br.app.fsantana.marketspaceapi.domain.services.RefreshTokenService;
import br.app.fsantana.marketspaceapi.secutiry.models.Auth;
import br.app.fsantana.marketspaceapi.secutiry.services.SessionService;
import br.app.fsantana.marketspaceapi.secutiry.services.TokenService;
import br.app.fsantana.marketspaceapi.domain.exceptions.AppRuleException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

/**
 * Created by felip on 12/10/2025.
 */

@Service
@RequiredArgsConstructor
public class SessionServiceImpl implements SessionService {

    private final UserDataProvider userDataProvider;
    private final PasswordEncoder passwordEncoder;
    private final RefreshTokenService refreshTokenService;
    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;

    @Override
    public User createUser(User user) {
        Optional<User> byEmail = userDataProvider.findByEmail(user.getEmail());
        if (byEmail.isPresent()) {
            throw new AppRuleException("Invalid data try again with new datas");
        }
        Optional<User> byTel = userDataProvider.findByTel(user.getTel());
        if (byTel.isPresent()) {
            throw new AppRuleException("Invalid data try again with new datas");
        }
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        return userDataProvider.save(user);
    }

    @Override
    public Auth auth(String email, String password) {
        Authentication authenticate = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        email,
                        password
                )
        );
        User user = ((Auth) authenticate.getPrincipal()).getUser();
        String jwtToken = tokenService.generateToken(user);
        RefreshToken refreshToken = refreshTokenService.generate(user);
        return  new Auth(user, jwtToken, refreshToken.getId().toString());
    }

    @Override
    public Auth refreshToken(UUID refreshToken) {

        RefreshToken refreshTokenFind = refreshTokenService.findById(refreshToken);
        RefreshToken generate = refreshTokenService.generate(refreshTokenFind.getUser());
        String jwtToken = tokenService.generateToken(refreshTokenFind.getUser());

        return new Auth(refreshTokenFind.getUser(), jwtToken, generate.getId().toString());
    }

}
