package it.ap.notesapp.controller;

import it.ap.notesapp.entity.Utente;
import it.ap.notesapp.services.JwtService;
import it.ap.notesapp.services.UtenteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    @Autowired
    private UtenteService utenteService;

    public AuthController(AuthenticationManager authenticationManager, JwtService jwtService) {
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@RequestBody Utente loginRequest) {
        Authentication authentication =
                authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(
                                loginRequest.getUsername(),
                                loginRequest.getPassword()
                        )
                );

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        String jwt = jwtService.generaToken(userDetails.getUsername());
        Map<String, String> res = new HashMap<>();
        res.put("token", jwt);

        return ResponseEntity.ok(res);
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody Utente registerRequest){
        utenteService.createUtente(registerRequest);
        return ResponseEntity.ok("Ok");
    }
}
