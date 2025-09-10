package it.ap.notesapp.config;

import it.ap.notesapp.entity.Utente;
import it.ap.notesapp.repository.UtenteRepository;
import it.ap.notesapp.services.JwtService;
import it.ap.notesapp.services.UtenteService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.List;

@Configuration
public class SecurityConfig {

    @Bean
    public UserDetailsService userDetailsService(UtenteRepository utenteRepository) {
        return username -> {
            Utente utente = utenteRepository.findByUsername(username);
            if (utente == null){
                throw new UsernameNotFoundException("Utente non trovato");
            }
            return new User(utente.getUsername(), utente.getPassword(), List.of(new SimpleGrantedAuthority("ROLE_USER")));
        };
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http,
                                                   JwtService jwtService,
                                                   UtenteService utenteService) throws Exception {
        JwtFilter jwtFilter = new JwtFilter(jwtService, utenteService);

        http
                .cors(cors -> {})
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/auth/login").permitAll()
                        .anyRequest().authenticated()
                )
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance(); // Per test senza hash
        // oppure:
        // return new BCryptPasswordEncoder();   // Per produzione con hash
    }
}
