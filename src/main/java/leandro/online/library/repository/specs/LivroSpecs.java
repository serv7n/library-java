package leandro.online.library.repository.specs;

import leandro.online.library.model.Livro;
import org.springframework.data.jpa.domain.Specification;

public class LivroSpecs {
    public static Specification<Livro> isbnEqual(String isbn){
        return ((root, query, cb) -> cb.equal(root.get("isbn"), isbn));
    }
    public static Specification<Livro> tituloLike(String titulo){
        return ((root, query, cb)
                -> cb.like(cb.upper(root.get("titulo")),"%" + titulo.toUpperCase() + "%"));
    }
    public static Specification<Livro> generoEqual(String genero){
        return ((root, query, cb)
                -> cb.equal(root.get("genero"), genero));
    }
}
