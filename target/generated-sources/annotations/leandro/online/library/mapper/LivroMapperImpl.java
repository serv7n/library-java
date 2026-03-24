package leandro.online.library.mapper;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;
import javax.annotation.processing.Generated;
import leandro.online.library.Enum.generoLivro;
import leandro.online.library.dto.AutorResponseDTO;
import leandro.online.library.dto.LivroResponseDTO;
import leandro.online.library.dto.LivroResquestDTO;
import leandro.online.library.model.Livro;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-03-20T11:04:30-0300",
    comments = "version: 1.6.3, compiler: javac, environment: Java 21.0.10 (Ubuntu)"
)
@Component
public class LivroMapperImpl extends LivroMapper {

    @Override
    public Livro toLivro(LivroResquestDTO livroDTO) {
        if ( livroDTO == null ) {
            return null;
        }

        Livro livro = new Livro();

        livro.setIsbn( livroDTO.isbn() );
        livro.setTitulo( livroDTO.titulo() );
        livro.setDataPublicacao( livroDTO.dataPublicacao() );
        if ( livroDTO.genero() != null ) {
            livro.setGenero( Enum.valueOf( generoLivro.class, livroDTO.genero() ) );
        }
        livro.setPreco( livroDTO.preco() );

        livro.setAutor( autorRepository.findById(livroDTO.id_autor()).orElse(null) );

        return livro;
    }

    @Override
    public LivroResponseDTO toDTO(Livro livro) {
        if ( livro == null ) {
            return null;
        }

        UUID id = null;
        String isbn = null;
        String titulo = null;
        LocalDate dataPublicacao = null;
        String genero = null;
        BigDecimal preco = null;

        id = livro.getId();
        isbn = livro.getIsbn();
        titulo = livro.getTitulo();
        dataPublicacao = livro.getDataPublicacao();
        if ( livro.getGenero() != null ) {
            genero = livro.getGenero().name();
        }
        preco = livro.getPreco();

        AutorResponseDTO autor = livro.getAutor() != null ? autorMapper.toResponseDTO(livro.getAutor()) : null;

        LivroResponseDTO livroResponseDTO = new LivroResponseDTO( id, isbn, titulo, dataPublicacao, genero, preco, autor );

        return livroResponseDTO;
    }
}
