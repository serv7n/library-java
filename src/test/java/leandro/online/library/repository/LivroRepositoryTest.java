package leandro.online.library.repository;

import leandro.online.library.Enum.generoLivro;
import leandro.online.library.model.Autor;
import leandro.online.library.model.Livro;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@SpringBootTest
class LivroRepositoryTest {
    @Autowired
    LivroRepository livroRepository;
    @Autowired
    AutorRepository autorRepository;

    @Test
    public void salva(){
        var idPossivelAutor = UUID.fromString("59d4c870-d7d0-4ef1-9218-39f4809aef9b");
        Optional<Autor> possivelAutor = autorRepository.findById(idPossivelAutor);
        if (possivelAutor.isEmpty()) return;
        Livro livro = new Livro();
        livro.setIsbn("dadwaw");
        livro.setAutor(possivelAutor.get());
        livro.setGenero(generoLivro.CIENCIA);
        livro.setTitulo("Cieicias biologicas");
        livro.setDataPublicacao(LocalDate.now());
        livro.setPreco(BigDecimal.valueOf(100));
        livroRepository.save(livro);

    }
    @Test
    @Transactional
    public void lista(){
        List<Livro> livros= livroRepository.findAll();


        livros.forEach(System.out::println);
    }
    @Test
    public void atualizar(){

        Optional<Livro> plivro = livroRepository.findById(UUID.fromString("cab6972a-c333-4630-a776-7b1ee8a5abe2"));
        Optional<Autor> pautor = autorRepository.findById(UUID.fromString("89a4a291-7b6a-4ab3-b79d-5eab85d9f58a"));
//        outro autor
        if (plivro.isEmpty() || pautor.isEmpty()) return;
        Livro livro = plivro.get();
        Autor autor = pautor.get();

        livro.setIsbn("ACIU-HGST");
        livro.setAutor(autor);
        livro.setGenero(generoLivro.ROMANCE);
        livro.setTitulo("Violeta");
        livro.setDataPublicacao(LocalDate.now());
        livro.setPreco(BigDecimal.valueOf(100.5));
        livroRepository.save(livro);
    }
    @Test
    public void delete(){
        Optional<Livro> plivro = livroRepository.findById(UUID.fromString("89a4a291-7b6a-4ab3-b79d-5eab85d9f58a"));
        if (plivro.isEmpty()) return;
        Livro livro = plivro.get();
        livroRepository.delete(livro);
    }

    @Test
    public void salvarVarios(){
        Autor autor = new Autor();
        autor.setNome("Harry");
        autor.setNacionalidade("Estadonidense`");
        autor.setDataNascimento(LocalDate.of(2002,3,15));
        autorRepository.save(autor);
        List<Livro> livros =   new ArrayList<Livro>();
//        livros.add()
        livros.add(new Livro("ADAD","Harry Potter",LocalDate.now(),generoLivro.FANTASIA,BigDecimal.valueOf(120),autor));
        livros.add(new Livro("OBCS","Senhor dos aneis",LocalDate.now(),generoLivro.FANTASIA,BigDecimal.valueOf(130),autor));
        livros.add(new Livro("YPQR","The Valking",LocalDate.now(),generoLivro.FANTASIA,BigDecimal.valueOf(180),autor));

        livroRepository.saveAll(livros);

    }

    @Test
    public void pesquisar(){
        List<Livro> livros = livroRepository.findByTituloContaining("Harry ");
        livros.forEach(System.out::println);
    }

    @Test
    public  void listarTodosViaQuery(){
        livroRepository.findAllLivrosMetodh();

    }


}


