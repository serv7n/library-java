package leandro.online.library.common;

import leandro.online.library.dto.ErroMensageDTO;
import leandro.online.library.dto.ErrorCampo;

import leandro.online.library.exception.*;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.nio.file.AccessDeniedException;
import java.util.List;

@RestControllerAdvice
public class GlobalExceptionController {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_CONTENT)
    public ErroMensageDTO handlerCampoNaoPreenchido(MethodArgumentNotValidException e){
        List<FieldError> fieldError = e.getFieldErrors();
        List<ErrorCampo> errors =
                fieldError.stream()
                .map(err -> new ErrorCampo(err.getField(), err.getDefaultMessage()))
                .toList();
        return new
                ErroMensageDTO(HttpStatus.UNPROCESSABLE_CONTENT.value(), "Erro validacao",errors);
    }
    @ExceptionHandler(GeneroInvalidoException.class)
    @ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
    public ErroMensageDTO handlerGeneroInvalidoException(GeneroInvalidoException e){
        String mensage = e.getMessage();
        return new ErroMensageDTO(HttpStatus.NOT_ACCEPTABLE.value(), mensage,List.of());
    }

    @ExceptionHandler(IsbnDuplicadoException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErroMensageDTO handlerIsbnDuplicadoException(IsbnDuplicadoException e){
        String mensage = e.getMessage();
        return new ErroMensageDTO(HttpStatus.CONFLICT.value(), mensage,List.of());
    }
    @ExceptionHandler(RegistroDuplicadoException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErroMensageDTO handlerRegistroDuplicadoException(RegistroDuplicadoException e){
        String mensage = e.getMessage();
        return new ErroMensageDTO(HttpStatus.CONFLICT.value(), mensage,List.of());
    }
    @ExceptionHandler(OperacaoNaoPermitidaException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErroMensageDTO handlerOperacaoNaoPermitidaException(OperacaoNaoPermitidaException e){
        String mensage = e.getMessage();
        return new ErroMensageDTO(HttpStatus.CONFLICT.value(), mensage,List.of());
    }

    @ExceptionHandler(EntidadeNaoEncontradaException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErroMensageDTO handlerEntidadeNaoEncontrada(EntidadeNaoEncontradaException e){
        String message = e.getMessage();
        return new ErroMensageDTO(HttpStatus.NOT_FOUND.value(), message,List.of());
    }

    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErroMensageDTO handlerAcessoNegado(AccessDeniedException e){
        String mensage = e.getMessage();
        return new ErroMensageDTO(HttpStatus.NOT_FOUND.value(), "Voce nao tem autorizacao: "+mensage,List.of());
    }
    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErroMensageDTO handlerErroInesperado(RuntimeException e){
        String mensage = e.getMessage();
        return new ErroMensageDTO(HttpStatus.NOT_FOUND.value(), "Erro inesperado: "+mensage,List.of());
    }




}
