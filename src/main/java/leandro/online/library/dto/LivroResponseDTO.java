package leandro.online.library.dto;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public record LivroResponseDTO(
        UUID id,
        String isbn,
        String titulo,
        LocalDate dataPublicacao,
        String genero,
        BigDecimal preco,
        AutorResponseDTO autor
) {
}
