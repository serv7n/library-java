package leandro.online.library.mapper;

import java.time.LocalDate;
import javax.annotation.processing.Generated;
import leandro.online.library.dto.AutorRequestDTO;
import leandro.online.library.dto.AutorResponseDTO;
import leandro.online.library.model.Autor;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-03-20T11:04:30-0300",
    comments = "version: 1.6.3, compiler: javac, environment: Java 21.0.10 (Ubuntu)"
)
@Component
public class AutorMapperImpl implements AutorMapper {

    @Override
    public Autor toEntity(AutorRequestDTO dto) {
        if ( dto == null ) {
            return null;
        }

        Autor autor = new Autor();

        autor.setNome( dto.nome() );
        autor.setDataNascimento( dto.dataNascimento() );
        autor.setNacionalidade( dto.nacionalidade() );

        return autor;
    }

    @Override
    public AutorRequestDTO toRequestDTO(Autor autor) {
        if ( autor == null ) {
            return null;
        }

        String nome = null;
        LocalDate dataNascimento = null;
        String nacionalidade = null;

        nome = autor.getNome();
        dataNascimento = autor.getDataNascimento();
        nacionalidade = autor.getNacionalidade();

        AutorRequestDTO autorRequestDTO = new AutorRequestDTO( nome, dataNascimento, nacionalidade );

        return autorRequestDTO;
    }

    @Override
    public AutorResponseDTO toResponseDTO(Autor autor) {
        if ( autor == null ) {
            return null;
        }

        String nome = null;
        LocalDate dataNascimento = null;
        String nacionalidade = null;

        nome = autor.getNome();
        dataNascimento = autor.getDataNascimento();
        nacionalidade = autor.getNacionalidade();

        AutorResponseDTO autorResponseDTO = new AutorResponseDTO( nome, dataNascimento, nacionalidade );

        return autorResponseDTO;
    }
}
