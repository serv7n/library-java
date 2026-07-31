package leandro.online.library.security;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import leandro.online.library.model.Usuario;
import leandro.online.library.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Collections;

@Component
@RequiredArgsConstructor
public class LoginSocialSuccessHandler  extends SavedRequestAwareAuthenticationSuccessHandler {
    private final UsuarioService usuarioService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws ServletException, IOException {
        OAuth2AuthenticationToken token = (OAuth2AuthenticationToken) authentication;
        OAuth2User oAuth2User = token.getPrincipal();
        String email = oAuth2User.getAttributes().get("email").toString();

        Usuario usuario = usuarioService.obterPorEmail(email);

        if(usuario == null){
            usuario = cadUsuario(email);
        }
        authentication = new CustomAuthentication(usuario);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        super.onAuthenticationSuccess(request, response, authentication);

    }

    public Usuario cadUsuario(String email){
        Usuario usuario = new Usuario();
        usuario.setEmail(email);
        usuario.setLogin(email);
        usuario.setSenha(null);
        usuario.setRoles(Collections.singletonList("OPERATOR"));
        return usuario;
    }
}
