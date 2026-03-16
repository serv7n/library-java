package leandro.online.library.controller;

import jakarta.validation.Valid;
import jakarta.websocket.server.PathParam;
import leandro.online.library.dto.AutorResponseDTO;
import leandro.online.library.dto.LivroResponseDTO;
import leandro.online.library.dto.LivroResquestDTO;
import leandro.online.library.model.Autor;
import leandro.online.library.model.Livro;
import leandro.online.library.service.LivroService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("livro")

public class LivroController {
    private final LivroService livroService;
    public LivroController(LivroService livroService)
    {
        this.livroService = livroService;
    }
    @PostMapping
    public ResponseEntity<Void> salva(@RequestBody @Valid LivroResquestDTO livroDTO){
        URI url = livroService.salva(livroDTO);
        return   ResponseEntity.created(url).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> mostrar(@PathVariable UUID id) {
        Optional<Livro> livroOptianal = livroService.mostraLivro(id);
        if (livroOptianal.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Livro livro = livroOptianal.get();
        Autor autor = livro.getAutor();
        AutorResponseDTO autorDTO = null;
        if (autor != null) {
            autorDTO = new AutorResponseDTO(autor.getName(), autor.getDataNascimento(), autor.getNacionalidade());
        }
        LivroResponseDTO livroDTO = new LivroResponseDTO(livro.getId(),
                livro.getIsbn(),
                livro.getTitulo(),
                livro.getDataPublicacao(),
                livro.getGenero().toString(),
                livro.getPreco(),
                autorDTO
        );

        return ResponseEntity.ok(livroDTO);
    }



}
