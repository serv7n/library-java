package leandro.online.library.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
import leandro.online.library.model.Autor;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.time.LocalDate;
import java.util.List;

public record AutorRequestDTO(
        @NotBlank(message = "Nao pode ser null")
        @Size(max = 255, message = "precisa ser menor que 255 caracter")

        String nome,
        @NotNull(message = "Nao pode ser null")
        @Past(message = "nao pode ser futuro")
        LocalDate dataNascimento,
        @Size(max = 254, message = "precisa ser menor que 255 caracter")
        @NotBlank(message = "Nao pode ser null")
        String nacionalidade) {
    public Autor mappear(){
        Autor autor = new Autor();
        autor.setName(nome);
        autor.setDataNascimento(dataNascimento);
        autor.setNacionalidade(nacionalidade);
        return autor;
    }
}
