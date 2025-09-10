package it.ap.notesapp.config;

import it.ap.notesapp.services.JwtService;
import it.ap.notesapp.services.UtenteService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

public class JwtFilter extends OncePerRequestFilter {
    private final JwtService jwtService;
    private final UtenteService utenteService;

    public JwtFilter(JwtService jwtService, UtenteService utenteService) {
        this.jwtService = jwtService;
        this.utenteService = utenteService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        String path = request.getServletPath();
        if (path.equals("/api/auth/login")) {
            filterChain.doFilter(request, response);
            return;
        }

        final String authHeader = request.getHeader("Authorization"); //prende l'header della richiesta http (con token)
        String username = null;
        String jwtToken = null;

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            jwtToken = authHeader.substring(7); //estrae token rimuovendo Bearer
            try {
                username = jwtService.estraiUsername(jwtToken);
            } catch (Exception e) {
                //token non valido
            }
        }

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = utenteService.findUserByUsername(username);
            if (jwtService.validaToken(jwtToken)) {
                UsernamePasswordAuthenticationToken authToken =
                        new UsernamePasswordAuthenticationToken(
                                userDetails,
                                null,
                                List.of(new SimpleGrantedAuthority("ROLE_USER"))
                        );
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }
        // Continua la catena di filtri
        filterChain.doFilter(request, response);
    }

}
