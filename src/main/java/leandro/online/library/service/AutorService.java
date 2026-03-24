package leandro.online.library.service;

import leandro.online.library.dto.AutorRequestDTO;
import leandro.online.library.dto.AutorResponseDTO;
import leandro.online.library.exception.OperacaoNaoPermitidaException;
import leandro.online.library.exception.RegistroDuplicadoException;
import leandro.online.library.mapper.AutorMapper;
import leandro.online.library.model.Autor;
import leandro.online.library.repository.AutorRepository;
import leandro.online.library.repository.LivroRepository;
import leandro.online.library.validator.AutorValidator;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

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

    public void salvar(Autor autor) {
        if(validator.existeAutorDuplicado(autor)) {
            throw new RegistroDuplicadoException("Autor Duplicado Tente Outro");
        }
        this.autorRepository.save(autor);
    }

    public Optional<Autor> findById(UUID id) {
        return autorRepository.findById(id);
    }

    public void deleteById(UUID id) {
        autorRepository.deleteById(id);
    }

    public void deleteAutor(Autor autor) {
        if(existLivro(autor)){
            throw  new OperacaoNaoPermitidaException("Erro na exclusão: registro está sendo utilizado.");
        };
        autorRepository.delete(autor);
    }

    public List<Autor> getAllAutor() {
        return autorRepository.findAll();
    }

    public List<AutorResponseDTO> mapperListDTO(List<Autor> autores){
        return autores.stream().map(autorMapper::toResponseDTO).toList();
    }

    public void atualizar(Autor autor, AutorRequestDTO autorRequestDTO){
        autor.setNome(autorRequestDTO.nome());
        autor.setDataNascimento(autorRequestDTO.dataNascimento());
        autor.setNacionalidade(autorRequestDTO.nacionalidade());
        if(validator.existeAutorDuplicado(autorMapper.toEntity( autorRequestDTO))){
            throw new RegistroDuplicadoException("Autor Duplicado tente inserir Outro");
        }
        autorRepository.save(autor);
    }

    public boolean existLivro(Autor autor) {
        return  livroRepository.existsLivroByAutor(autor);
    }
    public List<Autor> findByExemple(String nome, String nacionalidade){
        Autor autor =  new Autor();
        autor.setNome(nome);
        autor.setNacionalidade(nacionalidade);
        ExampleMatcher exemple = ExampleMatcher.matching().withIgnoreCase().withIgnoreNullValues().withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);
        Example<Autor> autorExample = Example.of(autor, exemple);
        return autorRepository.findAll(autorExample);
    }
}
