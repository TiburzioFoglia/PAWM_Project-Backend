package it.unicam.cs.PAWNProjectBackend.controller;


import it.unicam.cs.PAWNProjectBackend.model.Coordinate;
import it.unicam.cs.PAWNProjectBackend.model.Spiaggia;
import it.unicam.cs.PAWNProjectBackend.service.DBMSController;
import it.unicam.cs.PAWNProjectBackend.service.HandlerSpiaggia;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/administration")
@CrossOrigin(origins = "*")
@Slf4j
@RequiredArgsConstructor
public class AdministrationController {

    private final DBMSController dbmsController;
    private final HandlerSpiaggia handlerSpiaggia;

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
     * Aggiungi un ombrellone alla spiaggia
     * @param body body con i valori della richiesta
     * @return la spiaggia modificata
     */
    @PostMapping("/spiaggia/aggiungiOmbrellone")
    public ResponseEntity<Spiaggia> aggiungiOmbrellone(@RequestBody Map<String,Object> body){
        String nomeTipo = (String) body.get("nomeTipo");
        Coordinate coordinate = new Coordinate(Integer.parseInt((String) body.get("x")),Integer.parseInt((String) body.get("y")));
        log.info("nome tipo : {}", nomeTipo);
        log.info("coordinate : {}", coordinate);

        this.handlerSpiaggia.aggiungiOmbrellone(nomeTipo,coordinate);
        return ResponseEntity.ok(this.dbmsController.getSpiaggia());
    }

    /**
     * Modifica la griglia spiaggia
     * @param body body con i valori della richiesta
     * @return la nuova spiaggia
     */
    @PatchMapping("/spiaggia/modificaGrigliaSpiaggia")
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
     *
     * @param body
     * @return
     */
    @PostMapping("/spiaggia/modificaOmbrellone")
    public ResponseEntity<Spiaggia> modificaOmbrellone(Map<String,Object> body){

        Long id = (Long) body.get("id");
        String nomeTipo = (String) body.get("nomeTipo");
        Coordinate coordinate = (Coordinate) body.get("coordinate");

        //this.handlerSpiaggia.modificaOmbrellone(id,nomeTipo,coordinate);
        return ResponseEntity.ok(this.dbmsController.getSpiaggia());
    }



}
