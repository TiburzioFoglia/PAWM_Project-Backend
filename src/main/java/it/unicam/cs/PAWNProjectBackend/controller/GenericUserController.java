package it.unicam.cs.PAWNProjectBackend.controller;

import it.unicam.cs.PAWNProjectBackend.model.Prenotazione;
import it.unicam.cs.PAWNProjectBackend.model.Spiaggia;
import it.unicam.cs.PAWNProjectBackend.service.HandlerPrenotazione;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

@RestController
@RequestMapping("/user")
@CrossOrigin
@RequiredArgsConstructor
@Slf4j
public class GenericUserController {

    private HandlerPrenotazione handlerPrenotazione;

    /**
     *
     * @param body
     * @return
     */
    @PostMapping("/prenotaOmbrellone")
    public ResponseEntity<HttpEntity> prenotaOmbrellone(@RequestBody Map<String,Object> body){


        this.handlerPrenotazione.prenotaOmbrellone();
        return ResponseEntity.ok().build();
    }

}
