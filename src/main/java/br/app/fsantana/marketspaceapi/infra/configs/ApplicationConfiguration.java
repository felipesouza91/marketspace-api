package br.app.fsantana.marketspaceapi.infra.configs;

import br.app.fsantana.marketspaceapi.domain.dataprovider.UserDataProvider;
import br.app.fsantana.marketspaceapi.domain.models.User;
import br.app.fsantana.marketspaceapi.secutiry.models.Auth;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Configuration
@RequiredArgsConstructor
public class ApplicationConfiguration {

    private final UserDataProvider userDataProvider;

    @Bean
    @Transactional(readOnly = true)
    public UserDetailsService userDetailsService() {
        return username -> {
         User user =  userDataProvider.findByEmail(username)
                    .orElseThrow(() -> new UsernameNotFoundException("User not found"));
         return new Auth(user, null, null);
        };
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService());
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }
}