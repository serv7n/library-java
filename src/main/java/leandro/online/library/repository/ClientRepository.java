package leandro.online.library.repository;

import leandro.online.library.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.RestController;

@RestController
public interface ClientRepository extends JpaRepository<Client, Long> {
    Client findByClientId(String clientId);
}
