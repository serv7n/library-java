package leandro.online.library.repository;

import leandro.online.library.model.Autor;
import leandro.online.library.model.Livro;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface LivroRepository extends JpaRepository<Livro, UUID> {
    List<Livro> findByAutor(Autor autor);

    List<Livro> findByTituloContaining(String titulo);
//    metodo link diferente por conta do coitaing sem ele viraria um where

    Livro findFistByTitulo(String titulo, Sort sort);


}
