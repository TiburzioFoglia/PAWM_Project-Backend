package it.unicam.cs.PAWNProjectBackend.controller;

import it.unicam.cs.PAWNProjectBackend.model.Prenotazione;
import it.unicam.cs.PAWNProjectBackend.model.Spiaggia;
import it.unicam.cs.PAWNProjectBackend.model.User;
import it.unicam.cs.PAWNProjectBackend.service.DBMSController;
import it.unicam.cs.PAWNProjectBackend.service.HandlerPrenotazione;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@RequestMapping("/user")
@CrossOrigin
@RequiredArgsConstructor
@Slf4j
public class GenericUserController {

    private final HandlerPrenotazione handlerPrenotazione;
    private final DBMSController dbmsController;

    /**
     *
     * @param body
     * @return
     */
    @PostMapping("/prenotaOmbrellone")
    @PreAuthorize("hasRole('User')")
    public ResponseEntity<HttpEntity> prenotaOmbrellone(@RequestBody Map<String,Object> body){
        String data = (String) body.get("data");
        log.info("{}",data);
        /*data = data.replaceAll("/","-");
        log.info("{}",data);*/
        Integer numeroLettini = Integer.parseInt((String) body.get("numeroLettini"));
        log.info("{}",numeroLettini);
        Long idOmbrellone = new Long( (Integer) body.get("idOmbrellone"));
        log.info("{}",idOmbrellone);
        String username = (String) body.get("userName");
        log.info("{}",username);
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH);
        Date date = null;
        try {
            date = formatter.parse(data);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        this.handlerPrenotazione.prenotaOmbrellone(date,numeroLettini,idOmbrellone,username);
        return ResponseEntity.ok().build();
    }


    @GetMapping("/prenotazioni/all")
    @PreAuthorize("hasRole('User')")
    public ResponseEntity<Collection<Prenotazione>> getPrenotazioniUtente(@RequestParam String userName){
        User user = this.dbmsController.getUserByUserName(userName);
        return ResponseEntity.ok(this.dbmsController.getPrenotazioniUtente(user));
    }

}
