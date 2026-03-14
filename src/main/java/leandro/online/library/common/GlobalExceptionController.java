package leandro.online.library.common;

import leandro.online.library.dto.AutorResponseDTO;
import leandro.online.library.dto.ErroMensageDTO;
import leandro.online.library.dto.ErrorCampo;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
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
}
