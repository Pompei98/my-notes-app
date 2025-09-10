package it.ap.notesapp.services;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;

@Service
public class JwtService {
    private final SecretKey SECRET_KEY = Keys.secretKeyFor(SignatureAlgorithm.HS512);;
    private final long EXPIRATION_TIME = 1000 * 60 * 60;

    public String generaToken(String username) {
        return Jwts.builder()
                .setSubject(username) // inserisce username nel token
                .setIssuedAt(new Date()) // data creazione token
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME)) // scadenza token
                .signWith(SignatureAlgorithm.HS512, SECRET_KEY) // firma con algoritmo e chiave segreta
                .compact();
    }

    public String estraiUsername(String token) {
        return Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public Boolean validaToken(String token) {
        try {
            Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }
}
