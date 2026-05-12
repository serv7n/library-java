package leandro.online.library.mapper;

import leandro.online.library.dto.UsuarioRequestDTO;
import leandro.online.library.model.Usuario;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UsuarioMapper {
    Usuario toEntity(UsuarioRequestDTO dto);
}
