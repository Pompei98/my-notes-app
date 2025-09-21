package it.ap.notesapp.services;

import it.ap.notesapp.entity.Utente;
import it.ap.notesapp.repository.UtenteRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UtenteService {
    private UtenteRepository utenteRepository;

    public UtenteService(UtenteRepository utenteRepository){
        this.utenteRepository = utenteRepository;
    }

    public Utente findByUtenteId(Long id) {
        return utenteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Non riuscito"));
    }


    public Optional<Utente> findByUsernameAndPassword(String username, String password) {
        return utenteRepository.findByUsernameAndPassword(username, password);
    }

    public Utente findByUsername(String username) {
        return utenteRepository.findByUsername(username);
    }

    public UserDetails findUserByUsername(String username) {
        Utente utente = utenteRepository.findByUsername(username);
        if (utente == null){
            throw new UsernameNotFoundException("Noneee");
        }
        return new User(utente.getUsername(), utente.getPassword(), List.of(new SimpleGrantedAuthority("ROLE_USER")));
    }

    public Utente createUtente(Utente utente) {
        return utenteRepository.save(utente);
    }
}
