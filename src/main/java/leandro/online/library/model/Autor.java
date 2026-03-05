package leandro.online.library.model;

import jakarta.persistence.*;
import jakarta.persistence.criteria.Fetch;
import lombok.Getter;
import lombok.Setter;


import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Entity

@Table(name =  "autor", schema = "public")
@Getter
@Setter
public class Autor {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "name", length = 100,nullable = false)
    private String name;

    @Column(name = "data_nascimento",nullable = false)
    private LocalDate dataNascimento;

    private String nacionalidade;

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
