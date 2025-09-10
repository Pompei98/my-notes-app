package it.ap.notesapp.dto;

import java.time.LocalDateTime;

public class NotaDto {
    private Long idNota;
    private String titolo;
    private String testo;

    public NotaDto(Long idNota, String titolo, String testo, LocalDateTime dataCreazione) {
        this.idNota = idNota;
        this.titolo = titolo;
        this.testo = testo;
        this.dataCreazione = dataCreazione;
    }

    private LocalDateTime dataCreazione;

    public Long getIdNota() {
        return idNota;
    }

    public void setIdNota(Long idNota) {
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
}
