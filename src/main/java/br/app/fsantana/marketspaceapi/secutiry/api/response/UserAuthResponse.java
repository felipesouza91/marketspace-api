package br.app.fsantana.marketspaceapi.secutiry.api.response;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

/**
 * Created by felip on 12/10/2025.
 */

@Getter
@Setter
public class UserAuthResponse {

    private UUID id;
    private String name;
    private String tel;
    private String avatar;
}
