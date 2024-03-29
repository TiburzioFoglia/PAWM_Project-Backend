package it.unicam.cs.PAWNProjectBackend.controller;


import it.unicam.cs.PAWNProjectBackend.model.Coordinate;
import it.unicam.cs.PAWNProjectBackend.model.Ombrellone;
import it.unicam.cs.PAWNProjectBackend.model.Spiaggia;
import it.unicam.cs.PAWNProjectBackend.model.TipologiaOmbrellone;
import it.unicam.cs.PAWNProjectBackend.service.DBMSController;
import it.unicam.cs.PAWNProjectBackend.service.HandlerListino;
import it.unicam.cs.PAWNProjectBackend.service.HandlerSpiaggia;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

@RestController
@RequestMapping("/administration")
@CrossOrigin
@Slf4j
@RequiredArgsConstructor
public class AdministrationController {

    private final DBMSController dbmsController;
    private final HandlerSpiaggia handlerSpiaggia;
    private final HandlerListino handlerListino;


    /**
     * Aggiungi la griglia spiaggia al db
     * @param body body con i valori della richiesta
     * @return la nuova spiaggia
     */
    @PostMapping("/spiaggia/aggiungiGrigliaSpiaggia")
    public ResponseEntity<Spiaggia> aggiungiGrigliaSpiaggia(@RequestBody Map<String,Object>  body){
        ArrayList<Integer> griglia = new ArrayList<>(((Collection<String>) body.get("griglia")).stream().
                map(Integer::parseInt).toList());
        log.info("griglia: {}", griglia);
        this.handlerSpiaggia.aggiungiGrigliaSpiaggia(griglia);
        return ResponseEntity.ok(this.dbmsController.getSpiaggia());
    }

    /**
     * Modifica la griglia spiaggia
     * @param body body con i valori della richiesta
     * @return la nuova spiaggia
     */
    @PutMapping("/spiaggia/modificaGrigliaSpiaggia")
    public ResponseEntity<Spiaggia> modificaGrigliaSpiaggia(@RequestBody Map<String,Object>  body){
        ArrayList<Integer> griglia = new ArrayList<>(((Collection<String>) body.get("griglia")).stream().
                map(Integer::parseInt).toList());

        ArrayList<Long> listaIdOmbrelloni =
                new ArrayList<>(((Collection<String>) body.get("listaIdPostiConOmbrelloni")).stream().
                        map(i -> i == null ? -1 : Long.parseLong(i)).toList());

        log.info("griglia: {}", griglia);
        log.info("listaIdOmbrelloni: {}", listaIdOmbrelloni);
        log.info("Controllo : {}", listaIdOmbrelloni.get(0) == -1);

        this.handlerSpiaggia.modificaGrigliaSpiaggia(griglia,listaIdOmbrelloni);
        return ResponseEntity.ok(this.dbmsController.getSpiaggia());
    }


    /**
     * Aggiungi un ombrellone alla spiaggia
     * @param tipologiaOmbrellone body con i valori della richiesta
     * @return la spiaggia modificata
     */
    @PostMapping("/spiaggia/aggiungiOmbrellone")
    @PreAuthorize("hasRole('Admin')")
    public ResponseEntity<Spiaggia> aggiungiOmbrellone(@RequestBody TipologiaOmbrellone tipologiaOmbrellone, @RequestParam Long idPosto){
        Long idTipo = tipologiaOmbrellone.getId();
        log.info("Tipologia : {}", tipologiaOmbrellone);
        TipologiaOmbrellone tipologia = this.dbmsController.getTipologiaOmbrelloneFromId(idTipo);
        Ombrellone ombrellone = this.dbmsController.getSpiaggia().getOmbrelloneById(idPosto);
        Coordinate coordinate = ombrellone.getLocation();
        log.info("Tipologia : {}", tipologia);
        log.info("Coordinate : {}", coordinate);

        this.handlerSpiaggia.aggiungiOmbrellone(tipologia,coordinate);
        return ResponseEntity.ok(this.dbmsController.getSpiaggia());
    }


    /**
     * Elimina un ombrellone dalla spiaggia
     * @param id body con i valori della richiesta
     * @return la spiaggia modificata
     */
    @DeleteMapping("/spiaggia/deleteOmbrellone")
    @PreAuthorize("hasRole('Admin')")
    public ResponseEntity<Spiaggia> deleteOmbrellone(@RequestParam Long id){
        this.handlerSpiaggia.deleteOmbrelloneById(id);
        return ResponseEntity.ok(this.dbmsController.getSpiaggia());
    }


    /**
     * Aggiunge una tipologia ombrellone alla spiaggia
     * @param body body con i valori della richiesta
     * @return la nuova tipologia
     */
    @PostMapping("/listino/aggiungiTipologiaOmbrellone")
    @PreAuthorize("hasRole('Admin')")
    public ResponseEntity<TipologiaOmbrellone> aggiungiTipologiaOmbrellone(@RequestBody Map<String,Object> body){
        String nome = (String) body.get("nome");
        String descrizione = (String) body.get("descrizione");
        Double prezzo = Double.parseDouble((String) body.get("prezzo"));

        TipologiaOmbrellone tipologiaOmbrellone = this.handlerListino.aggiungiTipologiaOmbrellone(nome,descrizione,prezzo);
        return ResponseEntity.ok(tipologiaOmbrellone);
    }

    /**
     * Elimina una tipologia ombrellone
     * @param id l'id della tipologia
     * @return la lista delle tipologie
     */
    @DeleteMapping("/listino/deleteTipologiaOmbrellone")
    @PreAuthorize("hasRole('Admin')")
    public ResponseEntity<Collection<TipologiaOmbrellone>> deleteTipologiaOmbrellone(@RequestParam Long id){
        this.handlerListino.deleteTipologiaOmbrelloneById(id);
        return ResponseEntity.ok(this.dbmsController.getListaTipologieOmbrellone());
    }


    //aggiungere metodi fascia





}
