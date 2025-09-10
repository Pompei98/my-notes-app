package it.ap.notesapp.controller;

import it.ap.notesapp.dto.NotaDto;
import it.ap.notesapp.entity.Nota;
import it.ap.notesapp.entity.Utente;
import it.ap.notesapp.repository.NotaRepository;
import it.ap.notesapp.services.NotaService;
import it.ap.notesapp.services.UtenteService;
import org.springframework.security.core.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api/note")
public class NotaController {

    @Autowired
    private NotaService notaService;
    @Autowired
    private UtenteService utenteService;

    @GetMapping()
    public ResponseEntity<List<NotaDto>> getNotes(Authentication authentication) {
        String username = authentication.getName();
        Utente utente = utenteService.findByUsername(username);
        List<NotaDto> note = notaService.getNotesDtoByUtente(utente);
        return ResponseEntity.ok(note);
    }

    @PostMapping
    public ResponseEntity<NotaDto> createNote(@RequestBody Nota nota, @AuthenticationPrincipal UserDetails userDetails) {
        Utente utente = utenteService.findByUsername(userDetails.getUsername());
        nota.setUtente(utente);
        Nota savedNote = notaService.createNote(nota);
        URI uri = URI.create("/api/note/" + savedNote.getIdNota());
        return ResponseEntity.created(uri).body(new NotaDto(savedNote.getIdNota(), savedNote.getTitolo(), savedNote.getTesto(), savedNote.getDataCreazione()));
    }

    @PutMapping("/{id}")
    public ResponseEntity<NotaDto> updateNota(@PathVariable Long id, @RequestBody Nota nota) {
        try {
            Nota savedNote = notaService.updateNota(id, nota);
            return ResponseEntity.ok(new NotaDto(savedNote.getIdNota(), savedNote.getTitolo(), savedNote.getTesto(), savedNote.getDataCreazione()));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void>  deleteNota(@PathVariable Long id) {
        try {
            notaService.deleteNota(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
