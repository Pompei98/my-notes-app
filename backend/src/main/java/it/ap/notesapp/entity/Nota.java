package it.ap.notesapp.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "note")
public class Nota {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_nota")
    private long idNota;

    @Column(name = "titolo")
    private String titolo;

    @Column(name = "testo")
    private String testo;

    @CreationTimestamp
    @Column(name = "data_creazione", updatable = false)
    private LocalDateTime dataCreazione;

    @ManyToOne
    @JoinColumn(name = "id_utente")
    private Utente utente;

    public Nota() {}

    public Nota(String titolo, String testo, LocalDateTime dataCreazione){
        this.titolo = titolo;
        this.testo = testo;
        this.dataCreazione = dataCreazione;
    }

    public long getIdNota() {
        return idNota;
    }

    public void setIdNota(long idNota) {
        this.idNota = idNota;
    }

    public String getTitolo() {
        return titolo;
    }

    public void setTitolo(String titolo) {
        this.titolo = titolo;
    }

    public String getTesto() {
        return testo;
    }

    public void setTesto(String testo) {
        this.testo = testo;
    }

    public LocalDateTime getDataCreazione() {
        return dataCreazione;
    }

    public void setDataCreazione(LocalDateTime dataCreazione) {
        this.dataCreazione = dataCreazione;
    }

    public Utente getUtente() {
        return utente;
    }

    public void setUtente(Utente utente) {
        this.utente = utente;
    }
}


