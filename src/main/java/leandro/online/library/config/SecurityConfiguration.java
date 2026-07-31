package leandro.online.library.config;

import leandro.online.library.security.CustomUserDetailsService;
import leandro.online.library.security.LoginSocialSuccessHandler;
import leandro.online.library.service.UsuarioService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.core.GrantedAuthorityDefaults;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true,jsr250Enabled = true)
public class SecurityConfiguration {
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, LoginSocialSuccessHandler successHandler) throws Exception{
        return http.
                csrf(AbstractHttpConfigurer::disable).
                formLogin(configurer ->{
                    configurer.loginPage("/login").permitAll();
                }).
                httpBasic(Customizer.withDefaults()).
                authorizeHttpRequests(autorize ->{
                    autorize.requestMatchers("/login").permitAll();
                    autorize.requestMatchers(HttpMethod.POST,"/usuarios/**").permitAll();

                    autorize.anyRequest().authenticated();

                })
                .oauth2Login(oauth2 ->{
                    oauth2.loginPage("/login").permitAll();
                    oauth2.successHandler(successHandler);
                })
        .build();

    }

//    @Bean
    public UserDetailsService userDetailsService(UsuarioService usuarioService){

        return  new CustomUserDetailsService(usuarioService);
    }

    @Bean
    public GrantedAuthorityDefaults grantedAuthorityDefaults(){
        return  new GrantedAuthorityDefaults("");
    }
}
