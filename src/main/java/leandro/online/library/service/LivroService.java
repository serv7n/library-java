package leandro.online.library.service;

import leandro.online.library.Enum.generoLivro;
import leandro.online.library.dto.LivroResponseDTO;
import leandro.online.library.dto.LivroResquestDTO;
import leandro.online.library.exception.EntidadeNaoEncontradaException;
import leandro.online.library.mapper.AutorMapper;
import leandro.online.library.mapper.LivroMapper;
import leandro.online.library.model.Autor;
import leandro.online.library.model.Livro;
import leandro.online.library.repository.AutorRepository;
import leandro.online.library.repository.LivroRepository;
import leandro.online.library.validator.LivroValidator;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import static  leandro.online.library.repository.specs.LivroSpecs.*;



@Service
@AllArgsConstructor
public class LivroService {

    private final LivroRepository livroRepository;
    private final AutorRepository autorRepository;
    private final LivroValidator validator;
    private final LivroMapper livroMapper;
    private  final AutorMapper autorMapper;
    @Transactional
    public Livro salva(LivroResquestDTO livrodto) {
        validator.validarGenero(livrodto);
        validator.validarPrecoObrigatorioAPartirDe2020(livrodto.dataPublicacao(),livrodto.preco());
        Livro livro =  livroMapper.toLivro(livrodto);
        validator.existeIsbnDuplicado(livro);
        livroRepository.save(livro);
        return  livro;
    }
    @Transactional(readOnly = true)
    public Livro obterPorId(UUID id) {
        return  livroRepository.findById(id).orElseThrow(()->
                new EntidadeNaoEncontradaException("Erro entidade nao encontrada"));
    }
    @Transactional
    public void excluir(UUID id) {
        Livro livro =obterPorId(id);
        livroRepository.delete(livro);
    }
    public void atualizarEntidade(LivroResquestDTO dto, Livro livro){
        livro.setTitulo(dto.titulo());
        livro.setIsbn(dto.isbn());
        livro.setGenero(generoLivro.valueOf(dto.genero()));
        livro.setDataPublicacao(dto.dataPublicacao());
        livro.setPreco(dto.preco());
        Autor autor = null;
        if (dto.id_autor() != null) {
            autor = autorRepository.findById(dto.id_autor())
                    .orElseThrow(()->
                            new EntidadeNaoEncontradaException("Erro Autor do livro nao encontrado"));
        }
        livro.setAutor(autor);
    }
    @Transactional
    public void atualizarLivro(LivroResquestDTO dto, UUID id) {
        Livro livro  = obterPorId(id);
        validator.validarGenero(dto);
        validator.validarPrecoObrigatorioAPartirDe2020(dto.dataPublicacao(),dto.preco());
        atualizarEntidade(dto,livro);
        validator.existeIsbnDuplicado(livro);
    }

    public  List<LivroResponseDTO> pesquisa(

            String isbn,
            String titulo,
            Integer ano,
            String genero,
            BigDecimal preco,
            String nomeAutor) {
        Specification<Livro> specs = ((root, query, cb) -> cb.conjunction());
        if(isbn != null) specs = specs.and(isbnEqual(isbn));
        if(titulo != null) specs = specs.and(tituloLike(titulo));
        if(genero != null) specs = specs.and(generoEqual(genero));
        if(ano != null) specs = specs.and(anoPublicacaoEqual(ano));
        if(nomeAutor != null) specs = specs.and(nomeAutorLike(nomeAutor));
        List<Livro> livros = livroRepository.findAll(specs);
        return livros.stream().map(livroMapper::toDTO).toList();
    }

}