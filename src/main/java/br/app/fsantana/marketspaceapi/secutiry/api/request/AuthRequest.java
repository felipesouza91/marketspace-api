package br.app.fsantana.marketspaceapi.secutiry.api.request;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by felip on 12/10/2025.
 */

@Getter
@Setter
public class AuthRequest {
    private String email;
    private String password;
}
