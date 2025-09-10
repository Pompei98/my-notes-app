package it.ap.notesapp.controller;

import it.ap.notesapp.entity.Utente;
import it.ap.notesapp.services.UtenteService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "*")
public class UtenteController {

    private final UtenteService utenteService;

    public UtenteController(UtenteService utenteService) {
        this.utenteService = utenteService;
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody Utente loginRequest) {
        Optional<Utente> u = utenteService.findByUsernameAndPassword(loginRequest.getUsername(), loginRequest.getPassword());
        if(u.isPresent()) {
            return ResponseEntity.ok("bono");
        } else {
            return ResponseEntity.ok("mica bono");
        }
    }

}
