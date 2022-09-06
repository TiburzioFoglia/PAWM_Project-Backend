package it.unicam.cs.PAWNProjectBackend.service;

import it.unicam.cs.PAWNProjectBackend.model.*;
import it.unicam.cs.PAWNProjectBackend.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
@RequiredArgsConstructor
public class DBMSController {
    private final SpiaggiaRepository spiaggiaRepository;
    private final OmbrelloneRepository ombrelloneRepository;
    private final CoordinateRepository coordinateRepository;


    /**
     * Ottieni la vista della spiaggia dal db
     * @return la vista della spiaggia, null se non esiste
     */
    public Spiaggia getSpiaggia(){
        return this.spiaggiaRepository.findAll().stream().findFirst().orElse(null);
    }

    /**
     * Salva la spiaggia nel db
     * @param spiaggia la spiaggia da salvare
     */
    public void salvaSpiaggia(Spiaggia spiaggia) {
        this.spiaggiaRepository.save(spiaggia);
    }

    /**
     * Salva le coordinate nel db
     * @param coordinate le coordinate da salvare
     */
    public void salvaCoordinate(Coordinate coordinate) {
        this.coordinateRepository.save(coordinate);
    }

    /**
     * Salva l'ombrellone nel db
     * @param ombrellone l'ombrellone da salvare
     */
    public void salvaOmbrellone(Ombrellone ombrellone){
        this.ombrelloneRepository.save(ombrellone);
    }

    /**
     * Elimina le coordinate dal db
     * @param coordinate le coordinate da eliminare
     */
    public void deleteCoordinate(Coordinate coordinate) {
        this.coordinateRepository.delete(coordinate);
    }

    /**
     * Elimina un ombrellone dal db
     * @param ombrellone l'ombrellone da eliminare
     */
    public void deleteOmbrellone(Ombrellone ombrellone) {
        this.ombrelloneRepository.delete(ombrellone);
    }


    /**
     * Ottieni il listino prezzi dal db
     * @return il listino prezzi
     *//*
    public Listino ottieniListino() {
        return this.listinoRepository.findAll().stream().findFirst().orElseThrow();
    }*/

}