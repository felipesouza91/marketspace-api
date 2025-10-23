package br.app.fsantana.marketspaceapi.secutiry.services.impl;

import br.app.fsantana.marketspaceapi.domain.dataprovider.UserDataProvider;
import br.app.fsantana.marketspaceapi.domain.models.User;
import br.app.fsantana.marketspaceapi.secutiry.models.Auth;
import br.app.fsantana.marketspaceapi.secutiry.services.UserSessionService;
import br.app.fsantana.marketspaceapi.domain.exceptions.AppEntityNotFound;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * Created by felip on 13/10/2025.
 */

@Service
@RequiredArgsConstructor
public class UserSessionServiceImpl implements UserSessionService {

    private final UserDataProvider userDataProvider;
    private User user;


    @Override
    public User getCurrentUser() {
        Auth authentication = (Auth)  SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (Objects.isNull(user) || authentication.getUser().getId() != user.getId()) {
           user = userDataProvider.findById(authentication.getUser().getId()).orElseThrow(() -> new AppEntityNotFound("User not found"));
        }
        return user;
    }

}
