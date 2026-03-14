package leandro.online.library.dto;

import org.springframework.http.HttpStatus;

import java.util.List;

public record ErroMensageDTO(int status,
                             String message,
                             List<ErrorCampo> errors
                             )   {
    public  static ErroMensageDTO badRequest(String mensage){
        return new ErroMensageDTO(HttpStatus.BAD_REQUEST.value(), mensage, List.of());
    }
    public  static ErroMensageDTO conflict(String mensage){
        return new ErroMensageDTO(HttpStatus.CONFLICT.value(), mensage, List.of());
    }
}
