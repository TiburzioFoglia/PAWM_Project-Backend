package it.unicam.cs.PAWNProjectBackend.service;

import it.unicam.cs.PAWNProjectBackend.model.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
@Slf4j
public class HandlerListino {

    private Listino listinoGestito;
    private final DBMSController dbmsController;

    /**
     * Aggiorna il listino
     */
    public void ottieniListinoAggiornato() {
        this.listinoGestito = this.dbmsController.getListino();
    }

    /**
     * Questo metodo serve ad aggiungere una tipologia ombrellone
     * @param nome il nome della nuova tipologia
     * @param descrizione la descrizione della nuova tipologia
     * @param prezzo il moltiplicatore del prezzo della tipologia
     * @return la nuova tipologia
     */
    public TipologiaOmbrellone aggiungiTipologiaOmbrellone(String nome, String descrizione, Double prezzo) {
        this.ottieniListinoAggiornato();
        TipologiaOmbrellone nuovaTipologia = new TipologiaOmbrellone(nome,descrizione);
        this.dbmsController.salvaTipologiaOmbrellone(nuovaTipologia);
        log.info("La nuova tipologia creata: {}", nuovaTipologia);

        ListinoTipologiaOmbrelloneRel rel = new ListinoTipologiaOmbrelloneRel(nuovaTipologia,prezzo);
        this.listinoGestito.getPrezziTipologia().add(rel);
        this.dbmsController.salvaListino(this.listinoGestito);
        log.info("La nuova tipologia creata: {}", nuovaTipologia);

        return nuovaTipologia;
    }

    /**
     * Questo metodo serve a eliminare una tipologia ombrellone
     * @param id l'id della tipologia
     */
    public void deleteTipologiaOmbrelloneById(Long id) {
        TipologiaOmbrellone tipologiaOmbrellone = this.dbmsController.getTipologiaOmbrelloneFromId(id);
        log.info("tipologia ombrellone da eliminare: {}", tipologiaOmbrellone);
        this.dbmsController.deleteTipologiaOmbrellone(tipologiaOmbrellone);
    }

    /**
     *
     */
    public void aggiungiFasciaDiPrezzo() {

    }

    /**
     * Questo metodo serve a modificare una fascia di prezzo esistente
     */
    public void modificaFasciaDiPrezzo() {

    }

    /**
     *
     * @param fasciaDaModificare
     */
    private void eliminaFascia(FasciaDiPrezzo fasciaDaModificare) {

    }

}