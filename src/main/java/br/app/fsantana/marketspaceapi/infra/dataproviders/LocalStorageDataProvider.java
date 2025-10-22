package br.app.fsantana.marketspaceapi.infra.dataproviders;

import br.app.fsantana.marketspaceapi.domain.dataprovider.StorageDataProvider;

/**
 * Created by felip on 21/10/2025.
 */

public interface LocalStorageDataProvider extends StorageDataProvider {

    byte[] loadImage(String path);

}
