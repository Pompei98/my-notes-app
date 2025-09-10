package it.ap.notesapp.repository;

import it.ap.notesapp.entity.Nota;
import it.ap.notesapp.entity.Utente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotaRepository extends JpaRepository<Nota, Long> {
    List<Nota> findAllByUtente(Utente utente);
    List<Nota> getNotesByUtente(Utente utente);
}
