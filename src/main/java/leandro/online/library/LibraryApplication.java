package leandro.online.library;

import leandro.online.library.model.Autor;
import leandro.online.library.repository.AutorRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.LocalDate;

@SpringBootApplication
public class LibraryApplication {

    public static void main(String[] args) {
       SpringApplication.run(LibraryApplication.class, args);

    }

}
