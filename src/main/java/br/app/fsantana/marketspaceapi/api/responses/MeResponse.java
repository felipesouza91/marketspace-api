package br.app.fsantana.marketspaceapi.api.responses;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

/**
 * Created by felip on 15/10/2025.
 */

@Getter
@Setter
public class MeResponse {

    private UUID id;
    private String name;
    private String email;
    private String tel;
    private String avatar;
}
