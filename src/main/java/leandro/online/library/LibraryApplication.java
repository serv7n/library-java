package leandro.online.library;

import leandro.online.library.model.Autor;
import leandro.online.library.repository.AutorRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.time.LocalDate;

@SpringBootApplication
@EnableJpaAuditing
// permite agora que a entidade jpa esteja em costando mudancao assim quando um
//mudar ja muda automaticamente exemplo data_atualizacao e data_criacao
public class LibraryApplication {

    public static void main(String[] args) {
       SpringApplication.run(LibraryApplication.class, args);

    }

}
