package leandro.online.library.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.UUID;

@Entity
@Table(name = "client")
@Data
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private  UUID id;
    private  String clientId;
    private  String clientSecret;
    private String redirectUri;
    private String scope;



}
