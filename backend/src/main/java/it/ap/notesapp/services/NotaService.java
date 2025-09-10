package it.ap.notesapp.services;

import it.ap.notesapp.dto.NotaDto;
import it.ap.notesapp.entity.Nota;
import it.ap.notesapp.entity.Utente;
import it.ap.notesapp.repository.NotaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotaService {
    @Autowired
    private NotaRepository notaRepository;

    public NotaService(NotaRepository notaRepository) {
        this.notaRepository = notaRepository;
    }

    public List<NotaDto> getNotesDtoByUtente(Utente utente) {
        List<Nota> note = notaRepository.getNotesByUtente(utente);
        return note.stream()
                .map(nota -> new NotaDto(nota.getIdNota(), nota.getTitolo(), nota.getTesto(), nota.getDataCreazione()))
                .toList();
    }

    public Nota createNote(Nota nota) {
        return notaRepository.save(nota);
    }

    public Nota updateNota(Long id, Nota nota) {
        return notaRepository.findById(id)
                .map(existingNote -> {
                    existingNote.setTitolo(nota.getTitolo());
                    existingNote.setTesto(nota.getTesto());
                    return notaRepository.save(existingNote) ;
                })
                 .orElseThrow(() -> new RuntimeException("Nota non trovata con id: " + id));
    }

    public void deleteNota(Long id) {
        try {
            notaRepository.deleteById(id);
        } catch(EmptyResultDataAccessException e) {
            throw new RuntimeException("Nota con id:" + id + "non trovata");
        }
    }

    //public List<Nota> findByUsername(String username) {
      //  return
   // }
}
