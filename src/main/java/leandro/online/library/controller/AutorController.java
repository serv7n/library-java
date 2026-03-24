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
        List<Autor> autores = autorService.findByExemple(name,nacionalidade);
        List<AutorResponseDTO> autoresDTO = autorService.mapperListDTO(autores);
        return ResponseEntity.ok(autoresDTO);
    }

    @PostMapping
    public ResponseEntity<Object> salvar(@RequestBody @Valid AutorRequestDTO autor){
        Autor autorMap = autorMapper.toEntity(autor);
        autorService.salvar(autorMap);
        URI location = createHeaderLocation(autorMap.getId());
        return ResponseEntity.created(location).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<AutorResponseDTO> mostra(@PathVariable String id){
        UUID idv = UUID.fromString(id);
        return  autorService.findById(idv).map( autor-> {
           AutorResponseDTO autorDTO  = autorMapper.toResponseDTO(autor);
           return ResponseEntity.ok(autorDTO);
        }).orElseGet(()-> ResponseEntity.notFound().build());
    }


    @DeleteMapping("/{id}")
    public ResponseEntity deletar(@PathVariable String id){
        Optional<Autor> autor =  autorService.findById(UUID.fromString(id));
        if(autor.isEmpty()){
            return  ResponseEntity.badRequest().build();
        }
        autorService.deleteAutor(autor.get());
        return ResponseEntity.status(204).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> atualizar(@PathVariable String id, @RequestBody AutorRequestDTO autorRequestDTO){
        Optional<Autor> autorOptional = autorService.findById(UUID.fromString(id));
        if(autorOptional.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        autorService.atualizar(autorOptional.get(),autorRequestDTO);
        return  ResponseEntity.noContent().build();
    }
}
