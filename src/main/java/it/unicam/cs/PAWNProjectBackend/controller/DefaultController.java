package it.unicam.cs.PAWNProjectBackend.controller;

import it.unicam.cs.PAWNProjectBackend.model.Listino;
import it.unicam.cs.PAWNProjectBackend.model.ListinoTipologiaOmbrelloneRel;
import it.unicam.cs.PAWNProjectBackend.model.Spiaggia;
import it.unicam.cs.PAWNProjectBackend.model.TipologiaOmbrellone;
import it.unicam.cs.PAWNProjectBackend.service.DBMSController;
import it.unicam.cs.PAWNProjectBackend.service.HandlerListino;
import it.unicam.cs.PAWNProjectBackend.service.HandlerPrenotazione;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Map;

@RestController
@RequestMapping
@CrossOrigin
@RequiredArgsConstructor
@Slf4j
public class DefaultController {
    private final DBMSController dbmsController;
    private final HandlerPrenotazione handlerPrenotazione;

    private final HandlerListino handlerListino;

    /**
     * Ottieni la vista della spiaggia dal db
     * @return la vista della spiaggia
     */
    @GetMapping("/spiaggia/vista")
    @PreAuthorize("hasAnyRole('Admin','User')")
    public ResponseEntity<Spiaggia> getSpiaggia(){
        Spiaggia spiaggia = this.dbmsController.getSpiaggia();
        if (spiaggia == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(spiaggia);
    }

    /**
     * Ottieni la lista di tutte le tipologie ombrellone dal db
     * @return la lista delle tipologie ombrellone
     */
    @GetMapping("/spiaggia/listaTipologieOmbrellone")
    @PreAuthorize("hasRole('Admin')")
    public ResponseEntity<Collection<TipologiaOmbrellone>> getListaTipologieOmbrellone(){
        Collection<TipologiaOmbrellone> tipologie = this.dbmsController.getListaTipologieOmbrellone();
        if (tipologie == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(tipologie);
    }

    /**
     * Ottieni la lista di tutte le tipologie ombrellone dal db
     * @return la lista delle tipologie ombrellone
     */
    @GetMapping("/listino/listaTipologieOmbrelloneConPrezzo")
    @PreAuthorize("hasRole('Admin')")
    public ResponseEntity<Collection<ListinoTipologiaOmbrelloneRel>> getListaTipologieOmbrelloneConPrezzo(){
        Listino listino = this.handlerListino.getListinoGestito();
        if (listino == null || listino.getPrezziTipologia() == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(listino.getPrezziTipologia());
    }



    /**
     * Ottieni la lista di tutte le tipologie ombrellone dal db che non sono state utilizzate da nessun ombrellone
     * @return la lista delle tipologie ombrellone
     */
    @GetMapping("/spiaggia/listaTipologieOmbrelloneNonUtilizzate")
    @PreAuthorize("hasRole('Admin')")
    public ResponseEntity<Collection<TipologiaOmbrellone>> getListaTipologieOmbrelloneNonUtilizzate(){
        Collection<TipologiaOmbrellone> tipologie = this.dbmsController.getListaTipologieOmbrelloneNonUtilizzate();
        System.out.println(tipologie);
        if (tipologie == null) return ResponseEntity.ok(new ArrayList<>());
        return ResponseEntity.ok(tipologie);
    }

    @GetMapping("/listino/vista")
    public ResponseEntity<Listino> getListino(){
        Listino listino = this.dbmsController.getListino();
        if (listino == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(listino);
    }

    /**
     *
     * @param body
     * @return
     */
    @GetMapping("/ombrelloniPrenotabiliInData")
    public ResponseEntity<Collection<Integer>> getOmbrelloniPrenotabiliInData(@RequestBody Map<String,Object> body){
        Date data = (Date) body.get("date");
        Collection<Integer> idOmbrelloniPrenotabili = this.handlerPrenotazione.getOmbrelloniPrenotabili(data);
        return ResponseEntity.ok(idOmbrelloniPrenotabili);
    }
}
