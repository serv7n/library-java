package leandro.online.library.model;

import jakarta.persistence.*;
import jakarta.persistence.criteria.Fetch;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;


import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Entity

@Table(name =  "autor", schema = "public")
@Getter
@Setter
@EntityListeners(AuditingEntityListener.class)
// permite funcionar o createdata e lastmodify
public class Autor {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "name", length = 100,nullable = false)
    private String name;

    @Column(name = "data_nascimento",nullable = false)
    private LocalDate dataNascimento;

    @Column(name = "nacionalidade", nullable = false)
    private String nacionalidade;

    @CreatedDate
    @Column(name = "data_cadrastro")
    private LocalDate dataCadrastro;

    @LastModifiedDate
    @Column(name = "data_atualizacao")
    private LocalDate dataAtualizacao;
    //
    @Column(name = "id_user", nullable = true)
    private UUID idUser;

    @OneToMany(mappedBy ="autor", fetch = FetchType.LAZY)
    private List<Livro> livros;


    @Override
    public String toString() {
        return "Autor{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", dataNascimento=" + dataNascimento +
                ", nacionalidade='" + nacionalidade + '\''+
                ",livros {"+ livros+ "}";
    }
}
