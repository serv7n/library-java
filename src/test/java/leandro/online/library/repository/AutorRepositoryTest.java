package leandro.online.library.repository;

import leandro.online.library.dto.AutorRequestDTO;
import leandro.online.library.model.Autor;
import leandro.online.library.service.AutorService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@SpringBootTest
public class AutorRepositoryTest {
    @Autowired
    private AutorRepository autorRepository;
    AutorRepositoryTest(){

    }
    @Test
    public void salvar(){
        Autor autor = new Autor();
        autor.setNome("Leandro");
        autor.setNacionalidade("Brasileiro");
        autor.setDataNascimento(LocalDate.of(2007,7,23));
        var id = autorRepository.save(autor);
        System.out.println("salvouuuuuu " + id);
    }

    @Test
    public  void update(){
        var id = UUID.fromString("89a4a291-7b6a-4ab3-b79d-5eab85d9f58a");
        Optional<Autor> possivelAutor = autorRepository.findById(id);
        if(possivelAutor.isPresent()){
            System.out.println(possivelAutor.get());
            Autor autor = possivelAutor.get();
            autor.setNome("laoro");
            autor.setNacionalidade("Arabe");
            autorRepository.save(autor);
        }

    }

    @Test
    @Transactional
    public void listar(){
        List<Autor> autores =  autorRepository.findAll();
        autores.forEach(System.out::println);
    }

    @Test
    public void count(){
        long count = autorRepository.count();
        System.out.println(count);
    }

    @Test
    public void deletePorId(){
         var id = UUID.fromString("535adbe0-7038-4ffc-930c-bb10561f2c61");
         Optional<Autor> possivelAutor = autorRepository.findById(id);
         if(possivelAutor.isPresent()){
            Autor autor = possivelAutor.get();
            System.out.println(autor);
            autorRepository.delete(autor);

         }
    }

    @Test
    public void test(AutorService autorService){
       AutorRequestDTO autorRequestDTO = new AutorRequestDTO("leandro", LocalDate.now(), "brasileiro");
//       autorDTO.mappear();
       System.out.println();
    }
}
