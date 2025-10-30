package br.app.fsantana.marketspaceapi.domain.exceptions;

/**
 * Created by felip on 12/10/2025.
 */

public class AppEntityNotFound extends  RuntimeException{

    public AppEntityNotFound(String message) {
        super(message);
    }

    public AppEntityNotFound(String message, Throwable cause) {
        super(message, cause);
    }
}
