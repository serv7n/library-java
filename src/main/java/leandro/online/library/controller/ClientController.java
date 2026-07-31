package leandro.online.library.controller;

import leandro.online.library.model.Client;
import leandro.online.library.service.ClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class ClientController {

    private final ClientService clientService;
    private final PasswordEncoder passwordEncoder;
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasRole('GERENTE')")
    public void save(@RequestBody Client client) {
        String hashPassword =  passwordEncoder.encode(client.getClientSecret());
        client.setClientSecret(hashPassword);
        clientService.salve(client);

    }
}
