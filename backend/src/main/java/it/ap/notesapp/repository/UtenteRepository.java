package it.ap.notesapp.repository;

import it.ap.notesapp.entity.Utente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UtenteRepository extends JpaRepository<Utente, Long> {
    Optional<Utente> findByUsernameAndPassword(String username, String password);
    Utente findByUsername(String username);
    boolean existsByUsername(String username);
}
