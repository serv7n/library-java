package leandro.online.library.controller;
import jakarta.validation.Valid;
import leandro.online.library.dto.AutorRequestDTO;
import leandro.online.library.dto.AutorResponseDTO;
import leandro.online.library.mapper.AutorMapper;
import leandro.online.library.model.Autor;
import leandro.online.library.service.AutorService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/autores")
@AllArgsConstructor
public class AutorController implements GenericController {
    private final AutorService autorService;
    private final AutorMapper autorMapper;

    @GetMapping
    public ResponseEntity<List<AutorResponseDTO>> autor(
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "nacionalidade", required = false)    String nacionalidade
            ){
        List<AutorResponseDTO> autoresDTO = autorService.findByExemple(name,nacionalidade);
        return ResponseEntity.ok(autoresDTO);
    }

    @PostMapping
    public ResponseEntity<Object> salvar(@RequestBody @Valid AutorRequestDTO autorDTO){
        Autor autor = autorService.salvar(autorDTO);
        URI location = createHeaderLocation(autor.getId());
        return ResponseEntity.created(location).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<AutorResponseDTO> mostra(@PathVariable UUID id){
        return  ResponseEntity.ok(autorMapper.toResponseDTO(autorService.obterPorId(id)));
    }


    @DeleteMapping("/{id}")
    public ResponseEntity deletar(@PathVariable UUID id){
        autorService.deleteAutor(id);
        return ResponseEntity.status(204).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> atualizar(@PathVariable UUID id, @RequestBody AutorRequestDTO autorRequestDTO){
        autorService.atualizar(id,autorRequestDTO);
        return  ResponseEntity.noContent().build();
    }
}
