package it.unicam.cs.PAWNProjectBackend.controller;

import it.unicam.cs.PAWNProjectBackend.model.Listino;
import it.unicam.cs.PAWNProjectBackend.model.Spiaggia;
import it.unicam.cs.PAWNProjectBackend.model.TipologiaOmbrellone;
import it.unicam.cs.PAWNProjectBackend.service.DBMSController;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
@Slf4j
public class DefaultController {
    private final DBMSController dbmsController;

    /**
     * Ottieni la vista della spiaggia dal db
     * @return la vista della spiaggia
     */
    @GetMapping("/spiaggia/vista")
    public ResponseEntity<Spiaggia> getSpiaggia(){
        Spiaggia spiaggia = this.dbmsController.getSpiaggia();
        if (spiaggia == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(spiaggia);
    }

    /**
     * Ottieni la lista di tutte le tipologie ombrellone dal db
     * @return la lista delle tipologie ombrellone
     */
    @GetMapping("/spiaggia/listaTipologieOmbrellone") //TODO controllare
    public ResponseEntity<Collection<TipologiaOmbrellone>> getListaTipologieOmbrellone(){
        Collection<TipologiaOmbrellone> tipologie = this.dbmsController.getListaTipologieOmbrellone();
        if (tipologie == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(tipologie);
    }

    @GetMapping("/listino/vista")
    public ResponseEntity<Listino> getListino(){
        Listino listino = this.dbmsController.getListino();
        if (listino == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(listino);
    }
}
