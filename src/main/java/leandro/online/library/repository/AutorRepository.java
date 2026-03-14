package leandro.online.library.repository;

import leandro.online.library.model.Autor;
import leandro.online.library.model.Livro;
import org.springframework.data.domain.Limit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface AutorRepository extends JpaRepository<Autor, UUID> {
    List<Autor> findAutorByNameContaining(String name);
    List<Autor> findAutorByNacionalidade(String nacionalidade);
    List<Autor> findAutorsByNameContainingAndNacionalidade(String name, String nacionalidade);

    Optional<Autor> findAutorByNameAndDataNascimentoAndNacionalidade(String name, LocalDate dataNascimento, String nacionalidade);



}
