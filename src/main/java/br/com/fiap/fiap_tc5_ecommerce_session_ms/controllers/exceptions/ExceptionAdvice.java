package br.com.fiap.fiap_tc5_ecommerce_session_ms.controllers.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import br.com.fiap.fiap_tc5_ecommerce_session_ms.models.dtos.error.ErrorDto;

@ControllerAdvice
public class ExceptionAdvice {
    @ExceptionHandler({ RefusedTokenNotFounded.class })
    public ResponseEntity<ErrorDto> handleRuntimeException(RefusedTokenNotFounded runtimeException) {
        ErrorDto errorDto = new ErrorDto(
                "Ocorreu um erro interno",
                runtimeException.getMessage(),
                "422", null);

        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(errorDto);
    }

}
