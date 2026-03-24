package leandro.online.library.mapper;

import leandro.online.library.dto.LivroResponseDTO;
import leandro.online.library.dto.LivroResquestDTO;
import leandro.online.library.model.Livro;
import leandro.online.library.repository.AutorRepository;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring", uses = AutorMapper.class)
public abstract  class LivroMapper {
    @Autowired
    AutorRepository autorRepository;
    public  abstract Livro toLivro(LivroResquestDTO livroDTO);

    public  abstract LivroResponseDTO toDTO(Livro livro);
}
