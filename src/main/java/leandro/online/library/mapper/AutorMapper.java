package leandro.online.library.mapper;

import leandro.online.library.dto.AutorRequestDTO;
import leandro.online.library.dto.AutorResponseDTO;
import leandro.online.library.model.Autor;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AutorMapper {
    Autor toEntity(AutorRequestDTO dto);
    @Mapping(source = "nome", target = "nome")
    AutorRequestDTO toRequestDTO(Autor autor);
    @Mapping(source = "nome", target = "nome")
    AutorResponseDTO toResponseDTO(Autor autor);

}
