package it.unicam.cs.PAWNProjectBackend.service;

import it.unicam.cs.PAWNProjectBackend.model.*;
import it.unicam.cs.PAWNProjectBackend.repository.ListinoRepository;
import it.unicam.cs.PAWNProjectBackend.repository.OmbrelloneRepository;
import it.unicam.cs.PAWNProjectBackend.repository.SpiaggiaRepository;
import it.unicam.cs.PAWNProjectBackend.repository.TipologiaOmbrelloneRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
@RequiredArgsConstructor
public class DBMSController {
    private final SpiaggiaRepository spiaggiaRepository;
    private final ListinoRepository listinoRepository;
    private final OmbrelloneRepository ombrelloneRepository;



    public Spiaggia getSpiaggia(){
        return this.spiaggiaRepository.findAll().stream().findFirst().orElseThrow();
    }

    public Listino ottieniListino() {
        return this.listinoRepository.findAll().stream().findFirst().orElseThrow();
    }

    public void salvaSpiaggia(Spiaggia spiaggia) {
        this.spiaggiaRepository.save(spiaggia);
    }

    public void salvaOmbrellone(Ombrellone ombrellone){
        this.ombrelloneRepository.save(ombrellone);
    }
}