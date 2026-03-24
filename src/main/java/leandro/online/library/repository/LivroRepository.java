package leandro.online.library.repository;

import leandro.online.library.model.Autor;
import leandro.online.library.model.Livro;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
@Repository
public interface LivroRepository extends JpaRepository<Livro, UUID>, JpaSpecificationExecutor<Livro> {
    List<Livro> findByAutor(Autor autor);

    List<Livro> findByTituloContaining(String titulo);
//    metodo link diferente por conta do coitaing sem ele viraria um where

    Livro findFistByTitulo(String titulo, Sort sort);

    @Query("select l1_0.id from Livro l1_0 ")
//    tem que usar o model
    List<Livro> findAllLivrosMetodh();


    @Modifying
    @Transactional
    @Query("delete from Livro  l where l.id = :id")
    void deleteById(@Param("id") UUID id);


    boolean existsLivroByAutor(Autor autor);


    Optional<Livro> findLivroByIsbn(String isbn);
}
