package it.unicam.cs.PAWNProjectBackend.service;

import it.unicam.cs.PAWNProjectBackend.model.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;


@Service
@RequiredArgsConstructor
@Slf4j
public class HandlerListino {

    private Listino listinoGestito;

    private final DBMSController associatedDBMS;

    private HandlerSpiaggia handlerSpiaggiaAssociato;

    /*public void ottieniListinoAggiornato() {
        this.listinoGestito.setPrezziTipologia(this.associatedDBMS.getTipologieEPrezzo());
        this.listinoGestito.setPrezziFascia(this.associatedDBMS.ottieniMappaFasce());
        this.listinoGestito.setPrezzoBaseOmbrellone(this.associatedDBMS.ottieniPrezzoBaseOmbrellone());
        this.listinoGestito.setPrezzoBaseLettino(this.associatedDBMS.ottieniPrezzoBaseLettino());
    }*/


    public void aggiungiFasciaDiPrezzo() {

    }



    /**
     * Questo metodo serve a modificare una fascia di prezzo esistente
     */
    public void modificaFasciaDiPrezzo() {

    }


    /*private void modificaLocazione(FasciaDiPrezzo fasciaDaModificare, ArrayList<ArrayList<Ombrellone>> listaOmbrelloni) {
        this.outputVistaSpiaggiaFasce(this.vistaSpiaggiaFasce(listaOmbrelloni));

    }*/

    private void eliminaFascia(FasciaDiPrezzo fasciaDaModificare) {

    }

}