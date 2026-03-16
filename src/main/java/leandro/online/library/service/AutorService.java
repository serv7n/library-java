package leandro.online.library.service;

import leandro.online.library.dto.AutorRequestDTO;
import leandro.online.library.dto.AutorResponseDTO;
import leandro.online.library.exception.OperacaoNaoPermitidaException;
import leandro.online.library.model.Autor;
import leandro.online.library.repository.AutorRepository;
import leandro.online.library.repository.LivroRepository;
import leandro.online.library.validator.AutorValidator;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class AutorService {
    private final AutorRepository autorRepository;
    private final AutorValidator validator;
    private final LivroRepository livroRepository;

    public void salvar(Autor autor) {
        validator.validar(autor);
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

    public List<Autor> getAllAutorContainsName(String name) {
        return autorRepository.findAutorByNameContaining(name);
    }
    public List<Autor> getAllAutorNacionalidade(String nacionalidade) {
        return autorRepository.findAutorByNacionalidade(nacionalidade);
    }
    public List<Autor> getAllAutorContainsNameAndNacionalidade(String name, String nacionalidade) {
        return autorRepository.findAutorsByNameContainingAndNacionalidade(name, nacionalidade);
    }

    public List<AutorResponseDTO> mapperListDTO(List<Autor> autores){
        List<AutorResponseDTO> autoresDTO = autores.stream().map(autor -> new AutorResponseDTO(
                autor.getName(),
                autor.getDataNascimento(),
                autor.getNacionalidade())).toList();
        return autoresDTO;
    }

    public void atualizar(Autor autor, AutorRequestDTO autorRequestDTO){
        autor.setName(autorRequestDTO.nome());
        autor.setDataNascimento(autorRequestDTO.dataNascimento());
        autor.setNacionalidade(autorRequestDTO.nacionalidade());
        validator.validar(autorRequestDTO.mappear());
        autorRepository.save(autor);
    }

    public boolean existLivro(Autor autor) {
        return  livroRepository.existsLivroByAutor(autor);
    }
    public List<Autor> findByExemple(String nome, String nacionalidade){
        Autor autor =  new Autor();
        autor.setName(nome);
        autor.setNacionalidade(nacionalidade);
        ExampleMatcher exemple = ExampleMatcher.matching().withIgnoreCase().withIgnoreNullValues().withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);
        Example<Autor> autorExample = Example.of(autor, exemple);
        return autorRepository.findAll(autorExample);
    }
}
