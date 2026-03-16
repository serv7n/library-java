package leandro.online.library.dto;

import java.time.LocalDate;
import java.util.UUID;

public record AutorResponseDTO(
        String nome,
        LocalDate dataNascimento,
        String nacionalidade
) {
}
