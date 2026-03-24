package leandro.online.library.validator;

import leandro.online.library.Enum.generoLivro;
import leandro.online.library.dto.LivroResquestDTO;
import leandro.online.library.exception.GeneroInvalidoException;
import leandro.online.library.exception.IsbnDuplicadoException;
import leandro.online.library.model.Livro;
import leandro.online.library.repository.LivroRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@AllArgsConstructor
public class LivroValidator {
    private final LivroRepository livroRepository;
    public void validarGenero(LivroResquestDTO LivroDTO){
        try {
            generoLivro.valueOf(LivroDTO.genero());
        } catch (IllegalArgumentException e) {
            throw new GeneroInvalidoException("Genero Invalido");
        }


    }
    public void existeIsbnDuplicado(Livro livro){
        Optional<Livro> livroOp = livroRepository.findLivroByIsbn(livro.getIsbn());
        if(livroOp.isEmpty()) return;
        if(livro.getId() == null) throw new IsbnDuplicadoException("Isbn Duplicado");

        if(!livro.getId().equals(livroOp.get().getId())) throw new IsbnDuplicadoException("Isbn Duplicado");

    }

}
