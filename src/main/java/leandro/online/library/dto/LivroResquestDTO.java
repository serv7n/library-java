package leandro.online.library.dto;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.MapKeyEnumerated;
import jakarta.validation.constraints.*;
import leandro.online.library.Enum.generoLivro;
import leandro.online.library.model.Autor;
import leandro.online.library.model.Livro;
import org.springframework.format.annotation.NumberFormat;
import org.springframework.http.HttpStatusCode;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public record LivroResquestDTO(
        @NotBlank(message = "Campo Obrigatorio")
        @Size(max = 8,min = 8, message = "deve conter 8 caracter")
        String isbn,
        @NotBlank(message = "Campo Obrigatorio")
        @Size(max = 255, message = "deve ter no maximo 255 caracter")
        String titulo,
        @Past(message = "Data nao pode ser maio que o ano atual")
        LocalDate dataPublicacao,
//        @MapKeyEnumerated(generoLivro)
        @NotNull
        String genero,
        BigDecimal preco,
        @Null
        UUID id_autor
){

}
