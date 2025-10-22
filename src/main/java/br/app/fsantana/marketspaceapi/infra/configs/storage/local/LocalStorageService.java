package br.app.fsantana.marketspaceapi.infra.configs.storage.local;

import br.app.fsantana.marketspaceapi.infra.configs.storage.local.impl.response.FileResponseData;

/**
 * Created by felip on 21/10/2025.
 */

public interface LocalStorageService {

    FileResponseData loadFile(String fileName);
}
