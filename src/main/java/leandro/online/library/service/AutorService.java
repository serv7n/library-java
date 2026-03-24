package leandro.online.library.service;

import leandro.online.library.dto.AutorRequestDTO;
import leandro.online.library.dto.AutorResponseDTO;
import leandro.online.library.exception.EntidadeNaoEncontradaException;
import leandro.online.library.mapper.AutorMapper;
import leandro.online.library.model.Autor;
import leandro.online.library.repository.AutorRepository;
import leandro.online.library.repository.LivroRepository;
import leandro.online.library.validator.AutorValidator;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.springframework.data.jpa.domain.AbstractPersistable_.id;

@Service

public class AutorService {
    private final AutorRepository autorRepository;
    private final AutorValidator validator;
    private final LivroRepository livroRepository;
    private final AutorMapper autorMapper;

    public AutorService(AutorRepository autorRepository, AutorValidator validator, LivroRepository livroRepository, AutorMapper autorMapper) {
        this.autorRepository = autorRepository;
        this.validator = validator;
        this.livroRepository = livroRepository;
        this.autorMapper = autorMapper;
    }
    @Transactional
    public Autor salvar(AutorRequestDTO autorDTO) {
        Autor autor = autorMapper.toEntity(autorDTO);
        validator.existeAutorDuplicado(autor);
        autorRepository.save(autor);
        return autor;
    }

    public Autor obterPorId(UUID id) {
        return autorRepository.findById(id).orElseThrow(()-> new
                EntidadeNaoEncontradaException("Autor nao encontrado"));
    }
    @Transactional
    public void deleteAutor(UUID id) {
        Autor autor =  obterPorId(id);
        validator.existLivro(autor);
        autorRepository.delete(autor);
    }



    @Transactional
    public void atualizar(UUID id, AutorRequestDTO autorRequestDTO){
        Autor autor = obterPorId(id);
        autor.setNome(autorRequestDTO.nome());
        autor.setDataNascimento(autorRequestDTO.dataNascimento());
        autor.setNacionalidade(autorRequestDTO.nacionalidade());
        validator.existeAutorDuplicado(autor);
    }

    @Transactional(readOnly = true)
    public List<AutorResponseDTO> findByExemple(String nome, String nacionalidade){
        Autor autor =  new Autor();
        autor.setNome(nome);
        autor.setNacionalidade(nacionalidade);
        ExampleMatcher exemple = ExampleMatcher.matching().withIgnoreCase().withIgnoreNullValues().withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);
        Example<Autor> autorExample = Example.of(autor, exemple);
        return mapperListDTO(autorRepository.findAll(autorExample));
    }
    private List<AutorResponseDTO> mapperListDTO(List<Autor> autores){
        return autores.stream().map(autorMapper::toResponseDTO).toList();
    }
}
