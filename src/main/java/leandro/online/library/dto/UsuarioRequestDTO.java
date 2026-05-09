package leandro.online.library.dto;

import java.util.List;

public record UsuarioRequestDTO(String login, String senha, List<String> roles) {
}
