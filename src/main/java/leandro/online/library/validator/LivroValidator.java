package leandro.online.library.validator;

import leandro.online.library.Enum.generoLivro;
import leandro.online.library.dto.LivroResquestDTO;
import leandro.online.library.exception.GeneroInvalidoException;
import leandro.online.library.model.Livro;
import leandro.online.library.repository.LivroRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import java.util.Optional;

@Component
@AllArgsConstructor
public class LivroValidator {
    private final LivroRepository livroRepository;
    public void validarLivro(LivroResquestDTO LivroDTO){
        try {
            generoLivro.valueOf(LivroDTO.genero());
        } catch (IllegalArgumentException e) {
              throw  new  GeneroInvalidoException("Genero Invalido");
        }
    }
    public boolean existeIsbnDuplicado(Livro livro){
        Optional<Livro> livroOp = livroRepository.findLivroByIsbn(livro.getIsbn());
        if(livroOp.isEmpty()) return false;
        if(livro.getId() == null) return true;
        return !livro.getId().equals(livroOp.get().getId());

    }
}
