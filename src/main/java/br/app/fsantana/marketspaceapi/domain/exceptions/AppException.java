package br.app.fsantana.marketspaceapi.domain.exceptions;

/**
 * Created by felip on 15/10/2025.
 */

public class AppException extends RuntimeException{
    public AppException(String message) {
        super(message);
    }
}
