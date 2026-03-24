package leandro.online.library.validator;

import leandro.online.library.exception.OperacaoNaoPermitidaException;
import leandro.online.library.exception.RegistroDuplicadoException;
import leandro.online.library.model.Autor;
import leandro.online.library.repository.AutorRepository;
import leandro.online.library.repository.LivroRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@AllArgsConstructor
public class AutorValidator {
    final AutorRepository  autorRepository;
    final LivroRepository livroRepository;
    public void existeAutorDuplicado(Autor autor){

        Optional<Autor> encontrado = autorRepository
                .findAutorByNomeAndDataNascimentoAndNacionalidade(
                        autor.getNome(),
                        autor.getDataNascimento(),
                        autor.getNacionalidade()
                );
        // NÃO encontrou → não existe duplicidade
        if(encontrado.isEmpty()){
            return;
        }
        // Cadastro novo → se encontrou alguém já é duplicado
        if(autor.getId() == null){
            throw new RegistroDuplicadoException("Autor Duplicado Tente Outro");
        }
        // Update → verifica se é outro autor
         if(!autor.getId().equals(encontrado.get().getId())){
             throw new RegistroDuplicadoException("Autor Duplicado Tente Outro");
         }
    }
    public boolean existLivro(Autor autor) {
          if (livroRepository.existsLivroByAutor(autor)){
              throw  new OperacaoNaoPermitidaException("Erro na exclusão: registro está sendo utilizado.");
          }
    }
}
