package leandro.online.library.security;

import leandro.online.library.exception.EntidadeNaoEncontradaException;
import leandro.online.library.model.Usuario;
import leandro.online.library.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.Nullable;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CustomAuthenticationProvider implements AuthenticationProvider {
    private final UsuarioService usuarioService;
    private final PasswordEncoder encoder;

    @Override
    public @Nullable Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String login = authentication.getName();
        String senhaDigitada = authentication.getCredentials().toString();

        Usuario usuario = usuarioService.obterPorLogin(login);

        if(usuario == null){
            newEntidadeNaoEncotrdaExeption("Usuario Nao encontrado");
        }

        String senhaCriptografada = usuario.getSenha();
        Boolean senhasBatem = encoder.matches(senhaDigitada,senhaCriptografada);
        if(!senhasBatem){
            newEntidadeNaoEncotrdaExeption("Usuario Nao encontrado");
        }

        return new CustomAuthentication(usuario);


    }

    public void newEntidadeNaoEncotrdaExeption(String messege){
        throw  new EntidadeNaoEncontradaException(messege);
    }
    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.isAssignableFrom(UsernamePasswordAuthenticationToken.class);
    }
}
