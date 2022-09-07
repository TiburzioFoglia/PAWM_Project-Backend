package it.unicam.cs.PAWNProjectBackend.service;

import it.unicam.cs.PAWNProjectBackend.model.*;
import it.unicam.cs.PAWNProjectBackend.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;

@Service
@RequiredArgsConstructor
public class DBMSController {
    private final SpiaggiaRepository spiaggiaRepository;
    private final OmbrelloneRepository ombrelloneRepository;
    private final CoordinateRepository coordinateRepository;

    private final TipologiaOmbrelloneRepository tipologiaOmbrelloneRepository;


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
     * Ottieni una tipologia ombrellone dal suo id
     * @param idTipo l'id della tipologia
     * @return la tipologia ombrellone cercata
     */
    public TipologiaOmbrellone getTipologiaOmbrelloneFromId(Long idTipo) {
        return this.tipologiaOmbrelloneRepository.findAll().stream().filter(t -> t.getId().equals(idTipo)).findFirst().orElseThrow();
    }

    /**
     * Salva la tipologia ombrellone nel db
     * @param tipologia la tipologia da salvare
     */
    public void salvaTipologiaOmbrellone(TipologiaOmbrellone tipologia) {
        this.tipologiaOmbrelloneRepository.save(tipologia);
    }

    /**
     * Ottieni la lista di tutte le tipologie ombrellone
     * @return la lista delle tipologie ombrellone
     */
    public Collection<TipologiaOmbrellone> getListaTipologieOmbrellone() {
        return this.tipologiaOmbrelloneRepository.findAll().stream().toList();
    }


    /**
     * Ottieni il listino prezzi dal db
     * @return il listino prezzi
     *//*
    public Listino ottieniListino() {
        return this.listinoRepository.findAll().stream().findFirst().orElseThrow();
    }*/

}