package leandro.online.library.common;

import leandro.online.library.dto.ErroMensageDTO;
import leandro.online.library.dto.ErrorCampo;
import leandro.online.library.exception.AutorDuplicadoException;
import leandro.online.library.exception.GeneroInvalidoException;
import leandro.online.library.exception.IsbnDuplicadoException;
import leandro.online.library.exception.OperacaoNaoPermitidaException;
import leandro.online.library.exception.RegistroDuplicadoException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

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
        String meesege = e.getMessage();
        return new ErroMensageDTO(HttpStatus.NOT_ACCEPTABLE.value(), meesege,List.of());
    }

    @ExceptionHandler(IsbnDuplicadoException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErroMensageDTO handlerIsbnDuplicadoException(IsbnDuplicadoException e){
        String meesege = e.getMessage();
        return new ErroMensageDTO(HttpStatus.CONFLICT.value(), meesege,List.of());
    }
    @ExceptionHandler(RegistroDuplicadoException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErroMensageDTO handlerRegistroDuplicadoException(RegistroDuplicadoException e){
        String meesege = e.getMessage();
        return new ErroMensageDTO(HttpStatus.CONFLICT.value(), meesege,List.of());
    }
    @ExceptionHandler(OperacaoNaoPermitidaException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErroMensageDTO handlerOperacaoNaoPermitidaException(OperacaoNaoPermitidaException e){
        String meesege = e.getMessage();
        return new ErroMensageDTO(HttpStatus.CONFLICT.value(), meesege,List.of());
    }
}
