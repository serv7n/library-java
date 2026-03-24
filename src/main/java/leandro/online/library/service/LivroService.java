package leandro.online.library.service;

import leandro.online.library.Enum.generoLivro;
import leandro.online.library.dto.AutorResponseDTO;
import leandro.online.library.dto.LivroResponseDTO;
import leandro.online.library.dto.LivroResquestDTO;
import leandro.online.library.exception.GeneroInvalidoException;
import leandro.online.library.exception.IsbnDuplicadoException;
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
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.math.BigDecimal;
import java.net.URI;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class LivroService {

    private final LivroRepository livroRepository;
    private final AutorRepository autorRepository;
    private final LivroValidator validator;
    private final LivroMapper livroMapper;
    private  final AutorMapper autorMapper;

    public Livro salva(LivroResquestDTO LivroDTO) {

        if (!validator.validarLivro(LivroDTO)) throw new GeneroInvalidoException("Genero Invalido");

        Livro livro = livroMapper.toLivro(LivroDTO);

        if (validator.existeIsbnDuplicado(livro)) throw new IsbnDuplicadoException("Isbn Duplicado");

        if (LivroDTO.id_autor() != null) {
            Optional<Autor> autor = autorRepository.findById(LivroDTO.id_autor());
            autor.ifPresent(livro::setAutor);
        }

        livroRepository.save(livro);



        return livro;
    }

    public Optional<Livro> obterPorLivro(UUID id) {
        return livroRepository.findById(id);
    }

    public boolean excluir(UUID id) {

        Optional<Livro> livroOptional = livroRepository.findById(id);

        if (livroOptional.isEmpty()) {
            return false;
        }

        livroRepository.delete(livroOptional.get());
        return true;
    }

    public Optional<Livro> findLivro(UUID id) {
        return livroRepository.findById(id);
    }

    public void atualizarLivro(LivroResquestDTO livroResquestDTO, Livro livro) {

        if (!validator.validarLivro(livroResquestDTO))
            throw new GeneroInvalidoException("Genero Invalido");

        livro.setTitulo(livroResquestDTO.titulo());
        livro.setIsbn(livroResquestDTO.isbn());
        livro.setGenero(generoLivro.valueOf(livroResquestDTO.genero()));
        livro.setDataPublicacao(livroResquestDTO.dataPublicacao());
        livro.setPreco(livro.getPreco());

        Optional<Autor> autorOptional = Optional.empty();
        Autor autor = null;

        if (livroResquestDTO.id_autor() != null) {
            autorOptional = autorRepository.findById(livroResquestDTO.id_autor());
            if (autorOptional.isPresent()) {
                autor = autorOptional.get();
            }
        }

        livro.setAutor(autor);

        if (validator.existeIsbnDuplicado(livro))
            throw new IsbnDuplicadoException("ISBN duplicado");

        livroRepository.save(livro);

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

        ExampleMatcher exemple = ExampleMatcher
                .matching()
                .withIgnoreCase()
                .withIgnoreNullValues()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);

        Example<Livro> livroExample = Example.of(livro, exemple);

        List<Livro> livros = livroRepository.findAll(livroExample);

        List<LivroResponseDTO> livrosDTOs = mapperDTO(livros);

        return livrosDTOs;
    }

    private AutorResponseDTO toAutorDTO(Autor autor) {

        if (autor == null) return null;

        return new AutorResponseDTO(
                autor.getNome(),
                autor.getDataNascimento(),
                autor.getNacionalidade()
        );
    }

    private List<LivroResponseDTO> mapperDTO(List<Livro> livros) {

        List<LivroResponseDTO> livrosDTOs = livros.stream().map(l -> {

            AutorResponseDTO autorDTO = null;

            if (l.getAutor() != null) {
                autorDTO = autorMapper.toResponseDTO(l.getAutor());
            }

            return new LivroResponseDTO(
                    l.getId(),
                    l.getIsbn(),
                    l.getTitulo(),
                    l.getDataPublicacao(),
                    l.getGenero().toString(),
                    l.getPreco(),
                    autorDTO
            );

        }).toList();

        return livrosDTOs;
    }
}