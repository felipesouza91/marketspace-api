package br.app.fsantana.marketspaceapi.domain.exceptions;

/**
 * Created by felip on 12/10/2025.
 */

public class AppRuleException extends  RuntimeException{

    public AppRuleException(String message) {
        super(message);
    }

}
