package br.com.fiap.fiap_tc5_ecommerce_session_ms.controllers.exceptions;

public class RefusedTokenNotFounded extends Exception {

    public RefusedTokenNotFounded(String message) {
        super(message);
    }

    public RefusedTokenNotFounded(String message, Throwable cause) {
        super(message, cause);
    }

}
