package leandro.online.library.validator;

import leandro.online.library.exception.RegistroDuplicadoException;
import leandro.online.library.model.Autor;
import leandro.online.library.repository.AutorRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@AllArgsConstructor
public class AutorValidator {
    final AutorRepository  autorRepository;

    public boolean existeAutorDuplicado(Autor autor){

        Optional<Autor> encontrado = autorRepository
                .findAutorByNameAndDataNascimentoAndNacionalidade(
                        autor.getNome(),
                        autor.getDataNascimento(),
                        autor.getNacionalidade()
                );

        // NÃO encontrou → não existe duplicidade
        if(encontrado.isEmpty()){
            return false;
        }

        // Cadastro novo → se encontrou alguém já é duplicado
        if(autor.getId() == null){
            return true;
        }

        // Update → verifica se é outro autor
        return !autor.getId().equals(encontrado.get().getId());
    }
}
