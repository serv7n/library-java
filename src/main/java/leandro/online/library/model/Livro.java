package leandro.online.library.model;

import jakarta.persistence.*;
import leandro.online.library.Enum.generoLivro;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "livro")
@Getter
@Setter

@NoArgsConstructor

@EntityListeners(AuditingEntityListener.class)
public class Livro {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    UUID id;
    @Column(unique = true)
    String isbn;
    String titulo;

    @Column(name = "data_publicacao")
    LocalDate dataPublicacao;

    @Enumerated(EnumType.STRING)
    generoLivro genero;

    BigDecimal preco;

    @Column(name = "id_usuario")
    private UUID idUsuario;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_autor")
    private Autor autor;

    @Override
    public String toString() {
        return "Livro{" +
                "id=" + id +
                ", isbn='" + isbn + '\'' +
                ", titulo='" + titulo + '\'' +
                ", dataPublicacao=" + dataPublicacao +
                ", genero=" + genero +
                ", preco=" + preco +
                '}';
    }


    public Livro(String isbn, String titulo, LocalDate dataPublicacao, generoLivro genero, BigDecimal preco, Autor autor) {
        this.isbn = isbn;
        this.titulo = titulo;
        this.dataPublicacao = dataPublicacao;
        this.genero = genero;
        this.preco = preco;
        this.autor = autor;
    }
    public Livro(String isbn, String titulo, LocalDate dataPublicacao, generoLivro genero, BigDecimal preco) {
        this.isbn = isbn;
        this.titulo = titulo;
        this.dataPublicacao = dataPublicacao;
        this.genero = genero;
        this.preco = preco;
    }
}
