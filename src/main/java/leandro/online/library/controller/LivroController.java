package leandro.online.library.controller;

import jakarta.validation.Valid;
import leandro.online.library.dto.LivroResponseDTO;
import leandro.online.library.dto.LivroResquestDTO;
import leandro.online.library.mapper.LivroMapper;
import leandro.online.library.model.Livro;
import leandro.online.library.service.LivroService;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.awt.print.Pageable;
import java.math.BigDecimal;
import java.net.URI;
import java.time.LocalDate;
import java.util.List;
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
        Livro livro  = livroService.salva(livroDTO);
        URI url = createHeaderLocation(livro.getId());
        return   ResponseEntity.created(url).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<LivroResponseDTO> mostrar(@PathVariable UUID id) {
        return ResponseEntity.ok(livroMapper.toDTO(livroService.obterPorId(id)));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable UUID id) {
        livroService.excluir(id);
        return ResponseEntity.status(204).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> atualizar(
            @RequestBody @Valid LivroResquestDTO livroDTO,
            @PathVariable UUID id){
            livroService.atualizarLivro(livroDTO, id);
            return  ResponseEntity.status(204).build();
    }
//    ?isbn = adwdaw
    @GetMapping
    public ResponseEntity<Page<LivroResponseDTO>> pesquisar(
            @RequestParam(name = "isbn", required = false)
            String isbn,
            @RequestParam(name = "titulo", required = false)
            String titulo,
            @RequestParam(name = "data-publicacao", required = false)
            Integer ano,
            @RequestParam(name = "genero", required = false)
            String genero,

            @RequestParam(name = "nome-autor", required = false)
            String nomeAutor,
            @RequestParam(name = "pagina",required = false, defaultValue = "0")
            Integer pagina,
            @RequestParam(name = "tamanho-pagina", defaultValue = "10",required = false)
            Integer tamanhoPagina

    ){
       return ResponseEntity.ok(livroService.pesquisa(isbn, titulo, ano, genero, nomeAutor,pagina,tamanhoPagina));
    }

}
