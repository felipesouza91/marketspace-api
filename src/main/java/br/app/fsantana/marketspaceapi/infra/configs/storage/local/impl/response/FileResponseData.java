package br.app.fsantana.marketspaceapi.infra.configs.storage.local.impl.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by felip on 21/10/2025.
 */

@Getter
@Setter
@Builder
public class FileResponseData {

    byte[] content;
    String contentType;
    String fileName;
}
