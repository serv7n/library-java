package leandro.online.library.service;

import leandro.online.library.model.Client;
import leandro.online.library.repository.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ClientService {
    private final ClientRepository clientRepository;
    public Client salve(Client client) {
        return clientRepository.save(client);
    }
    public Client getByClientId(String clientId) {
        return clientRepository.findByClientId(clientId);
    }
}
