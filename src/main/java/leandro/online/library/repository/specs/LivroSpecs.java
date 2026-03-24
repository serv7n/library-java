package leandro.online.library.repository.specs;

import leandro.online.library.model.Livro;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.relational.core.sql.In;

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
    public static Specification<Livro> anoPublicacaoEqual(Integer ano){
//     and   to_char(data_publicao, "YYYY") = :ano
        return ((root, query, cb)
                -> cb.equal(cb.function("to_char", String.class, root.get("dataPublicacao"), cb.literal("YYYY")), ano));
    }
    public static Specification<Livro> nomeAutorLike(String nome){
//     and   to_char(data_publicao, "YYYY") = :ano
        return ((root, query, cb)
                -> cb.like(cb.upper(root.get("autor").get("nome")), "%"+ nome.toUpperCase() + "%"));
    }
}
