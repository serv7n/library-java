package leandro.online.library.controller;


import leandro.online.library.dto.UsuarioRequestDTO;
import leandro.online.library.mapper.UsuarioMapper;
import leandro.online.library.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("usuarios")
@RequiredArgsConstructor
public class UsuarioController {
    private final UsuarioService usuarioService;
    private final UsuarioMapper mapper;
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void salvar(@RequestBody UsuarioRequestDTO dto){
        usuarioService.salvar(mapper.toEntity(dto));
    }
}
