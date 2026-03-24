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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

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
        atualizarEntidade(dto,livro);
        validator.existeIsbnDuplicado(livro);
    }

    public List<LivroResponseDTO> pesquisa(
            String isbn,
            String titulo,
            LocalDate dataPublicacao,
            generoLivro genero,
            BigDecimal preco,
            String nomeAutor) {

        Livro livro = new Livro(isbn, titulo, dataPublicacao, genero, preco);
        Autor autor = null;
        if (nomeAutor != null) {
            autor = new Autor();
            autor.setNome(nomeAutor);
            livro.setAutor(autor);
        }
        ExampleMatcher example = ExampleMatcher
                .matching()
                .withIgnoreCase()
                .withIgnoreNullValues()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);

        Example<Livro> livroExample = Example.of(livro, example);

        List<Livro> livros = livroRepository.findAll(livroExample);

        return livros.stream().map(livroMapper::toDTO).toList();
    }

}