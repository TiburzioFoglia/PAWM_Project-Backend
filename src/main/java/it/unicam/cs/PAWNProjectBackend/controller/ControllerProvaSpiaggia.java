package it.unicam.cs.PAWNProjectBackend.controller;

import it.unicam.cs.PAWNProjectBackend.model.*;
import it.unicam.cs.PAWNProjectBackend.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;


@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class ControllerProvaSpiaggia {

    private final DBMSController dbmsController;

    private final HandlerSpiaggia handlerSpiaggia;



    @GetMapping("/spiaggia/vista")
    public ResponseEntity<Spiaggia> getSpiaggia(){
        return ResponseEntity.ok(this.dbmsController.getSpiaggia());
    }

    @PostMapping("/spiaggia/aggiungiOmbrellone")
    public ResponseEntity<Spiaggia> aggiungiOmbrellone(Map<String,Object> body){

        String nomeTipo = (String) body.get("nomeTipo");
        Coordinate coordinate = (Coordinate) body.get("coordinate");

        this.handlerSpiaggia.aggiungiOmbrellone(nomeTipo,coordinate);
        return ResponseEntity.ok(this.dbmsController.getSpiaggia());
    }

    @PostMapping("/spiaggia/modificaOmbrellone")
    public ResponseEntity<Spiaggia> modificaOmbrellone(Map<String,Object> body){

        Long id = (Long) body.get("id");
        String nomeTipo = (String) body.get("nomeTipo");
        Coordinate coordinate = (Coordinate) body.get("coordinate");

        this.handlerSpiaggia.modificaOmbrellone(id,nomeTipo,coordinate);
        return ResponseEntity.ok(this.dbmsController.getSpiaggia());
    }

    @PostMapping("/spiaggia/aggiungiGrigliaSpiaggia")
    public ResponseEntity<Spiaggia> aggiungiGrigliaSpiaggia(Map<String,Object> body){

        Map<String,Integer> griglia = new HashMap<>();

        for(String riga : body.keySet()){
            String numeroPosti = (String) body.get(riga);
            griglia.put(riga, Integer.parseInt(numeroPosti));
        }

        this.handlerSpiaggia.aggiungiGrigliaSpiaggia(griglia);
        return ResponseEntity.ok(this.dbmsController.getSpiaggia());
    }

}
