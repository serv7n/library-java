package leandro.online.library.controller;

import jakarta.validation.Valid;
import jakarta.websocket.server.PathParam;
import leandro.online.library.dto.AutorRequestDTO;
import leandro.online.library.dto.AutorResponseDTO;
import leandro.online.library.dto.ErroMensageDTO;
import leandro.online.library.exception.OperacaoNaoPermitida;
import leandro.online.library.model.Autor;
import leandro.online.library.service.AutorService;
import org.springframework.boot.jackson.autoconfigure.JacksonProperties;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/autores")
public class AutorController {
    private AutorService autorService;
    public AutorController(AutorService autorService){
        this.autorService = autorService;
    }

    @GetMapping
    public ResponseEntity<List<AutorResponseDTO>> autor(
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "nacionalidade", required = false)    String nacionalidade
            ){
        List<Autor> autores = List.of();
        if(name != null && nacionalidade != null){
                 autores = autorService.getAllAutorContainsNameAndNacionalidade(name,nacionalidade);

        }else if (name != null) {
               autores = autorService.getAllAutorContainsName(name);

        }else if (nacionalidade != null) {
                 autores = autorService.getAllAutorNacionalidade(nacionalidade);

        }else {
                 autores = autorService.getAllAutor();
        }

        List<AutorResponseDTO> autoresDTO = autorService.mapperListDTO(autores);
        return ResponseEntity.ok(autoresDTO);

    }
    @PostMapping
    public ResponseEntity<Object> salvar(@RequestBody @Valid AutorRequestDTO autor){
        Autor autorMap = autor.mappear();
        try {
            autorService.salvar(autorMap);
        } catch (RuntimeException e) {
            ErroMensageDTO erro = ErroMensageDTO.conflict("Erro de Validação");
            return ResponseEntity.status(erro.status()).body(erro);
        }

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(autorMap.getId())
                .toUri();

        return ResponseEntity.created(location).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<AutorResponseDTO> mostra(@PathVariable String id){

        Optional<Autor> autorOptional = autorService.findById(UUID.fromString(id));
        if(autorOptional.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        Autor autor = autorOptional.get();
        AutorResponseDTO autorDTO = new AutorResponseDTO(
                autor.getId(),
                autor.getName(),
                autor.getDataNascimento(),
                autor.getNacionalidade());

        return ResponseEntity.ok(autorDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deletar(@PathVariable String id){
        Optional<Autor> autor =  autorService.findById(UUID.fromString(id));
        if(autor.isEmpty()){
            return  ResponseEntity.badRequest().build();
        }
        try {
            autorService.deleteAutor(autor.get());
        } catch (OperacaoNaoPermitida e) {
            ErroMensageDTO err = ErroMensageDTO.badRequest("Erro na exclusão: registro está sendo utilizado.");
            return ResponseEntity.status(err.status()).body(err);
        }

        return ResponseEntity.status(204).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> atualizar(@PathVariable String id, @RequestBody AutorRequestDTO autorRequestDTO){
        Optional<Autor> autorOptional = autorService.findById(UUID.fromString(id));
        if(autorOptional.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        try {
            autorService.atualizar(autorOptional.get(),autorRequestDTO);
        } catch (RuntimeException e) {
            ErroMensageDTO erro = ErroMensageDTO.conflict("Erro de Validação");
            return ResponseEntity.status(erro.status()).body(erro);
        }

        return  ResponseEntity.noContent().build();
    }

}
