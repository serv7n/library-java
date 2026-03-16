package leandro.online.library.service;


import leandro.online.library.Enum.generoLivro;
import leandro.online.library.dto.LivroResquestDTO;
import leandro.online.library.exception.IsbnDuplicadoException;
import leandro.online.library.model.Autor;
import leandro.online.library.model.Livro;
import leandro.online.library.repository.AutorRepository;
import leandro.online.library.repository.LivroRepository;
import leandro.online.library.validator.LivroValidator;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class LivroService {
    private final LivroRepository livroRepository;
    private final AutorRepository autorRepository;
    private final LivroValidator validator;
    public URI salva(LivroResquestDTO LivroDTO){
        validator.validarLivro(LivroDTO);
        Livro livro = new Livro(LivroDTO.isbn(),
                LivroDTO.titulo(),
                LivroDTO.dataPublicacao(),
                generoLivro.valueOf(LivroDTO.genero()),
                LivroDTO.preco());
//        verifica se o autor esta presente se estiver inseri no livro o autor

        if(validator.existeIsbnDuplicado(livro)){
            throw new IsbnDuplicadoException("Isbn Duplicado");
        }
        if(LivroDTO.id_autor() != null){
            Optional<Autor> autor = autorRepository.findById(LivroDTO.id_autor());
            autor.ifPresent(livro::setAutor);
        }
        livroRepository.save(livro);
        URI url = ServletUriComponentsBuilder.
                        fromCurrentRequest().
                        path("/{id}").
                        buildAndExpand(livro.getId()).
                        toUri();
        return url;
    }

    public Optional<Livro> mostraLivro(UUID id){
        Optional<Livro> livroOp =  livroRepository.findById(id);
        return livroOp;
    }
}
