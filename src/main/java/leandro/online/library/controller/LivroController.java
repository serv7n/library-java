package leandro.online.library.controller;

import jakarta.validation.Valid;
import leandro.online.library.dto.LivroResponseDTO;
import leandro.online.library.dto.LivroResquestDTO;
import leandro.online.library.mapper.LivroMapper;
import leandro.online.library.model.Livro;
import leandro.online.library.service.LivroService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("livros")

public class LivroController implements GenericController {
    private final LivroService livroService;
    private final LivroMapper livroMapper;

    public LivroController(LivroService livroService, LivroMapper livroMapper)
    {
        this.livroService = livroService;
        this.livroMapper = livroMapper;
    }
    @PostMapping
    public ResponseEntity<Void> salva(@RequestBody @Valid LivroResquestDTO livroDTO){
        Livro livro = livroService.salva(livroDTO);
        URI url = createHeaderLocation(livro.getId());
        return   ResponseEntity.created(url).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<LivroResponseDTO> mostrar(@PathVariable UUID id) {
        return livroService.obterPorLivro(id).map(l ->{
                var dto  = livroMapper.toDTO(l);
                return ResponseEntity.ok(dto);
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable UUID id){
        if(livroService.excluir(id)){
            return  ResponseEntity.status(204).build();
        }
        return ResponseEntity.notFound().build();

    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> atualizar(
            @RequestBody @Valid LivroResquestDTO livroDTO,
            @PathVariable UUID id){
            Optional<Livro> livro =  livroService.obterPorLivro(id);
            if(livro.isEmpty()) return ResponseEntity.notFound().build();
            livroService.atualizarLivro(livroDTO, livro.get());
            return  ResponseEntity.status(204).build();
    }


}
