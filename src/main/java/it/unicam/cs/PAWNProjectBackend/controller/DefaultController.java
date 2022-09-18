package it.unicam.cs.PAWNProjectBackend.controller;

import it.unicam.cs.PAWNProjectBackend.model.*;
import it.unicam.cs.PAWNProjectBackend.service.DBMSController;
import it.unicam.cs.PAWNProjectBackend.service.HandlerListino;
import it.unicam.cs.PAWNProjectBackend.service.HandlerPrenotazione;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

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
        if (tipologie == null) return ResponseEntity.ok(new ArrayList<>());
        return ResponseEntity.ok(tipologie);
    }

    /**
     *
     * @return
     */
    @GetMapping("/listino/vista")
    public ResponseEntity<Listino> getListino(){
        Listino listino = this.dbmsController.getListino();
        if (listino == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(listino);
    }

    /**
     *
     * @return
     */
    @GetMapping("/spiaggia/listaOmbrelloniConPrenotazione")
    @PreAuthorize("hasRole('Admin')")
    public ResponseEntity<Collection<Ombrellone>> getOmbrelloniConPrenotazione(){
        return ResponseEntity.ok(this.dbmsController.getOmbrelloniConPrenotazione());
    }

    /**
     *
     * @return
     */
    @GetMapping("/spiaggia/listaOmbrelloniPrenotatiInData")
    @PreAuthorize("hasRole('User')")
    public ResponseEntity<Collection<Ombrellone>> getOmbrelloniPrenotatiInData(@RequestParam String data){
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH);
        Date date = null;
        try {
            date = formatter.parse(data);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        return ResponseEntity.ok(this.dbmsController.getOmbrelloniPrenotatiInData(date));
    }
}
