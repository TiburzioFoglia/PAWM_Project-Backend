package it.unicam.cs.PAWNProjectBackend.service;

import it.unicam.cs.PAWNProjectBackend.model.*;
import it.unicam.cs.PAWNProjectBackend.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class DBMSController {
    private final SpiaggiaRepository spiaggiaRepository;
    private final OmbrelloneRepository ombrelloneRepository;
    private final CoordinateRepository coordinateRepository;
    private final TipologiaOmbrelloneRepository tipologiaOmbrelloneRepository;
    private final ListinoRepository listinoRepository;
    private final UserRepository userRepository;
    private final PrenotazioneRepository prenotazioneRepository;


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
     * Ottieni la lista di tutte le tipologie e dei relativi prezzi
     * @return la lista delle tipologie e dei relativi prezzi
     */
    public Collection<ListinoTipologiaOmbrelloneRel> getTipologieEPrezzo() {
        return this.listinoRepository.findAll().stream().findFirst().orElseThrow().getPrezziTipologia();
    }

    /**
     * Elimina una tipologia ombrellone dal db
     * @param tipologia la tipologia da eliminare
     */
    public void deleteTipologiaOmbrellone(TipologiaOmbrellone tipologia) {
        this.tipologiaOmbrelloneRepository.delete(tipologia);
    }

    /**
     * Salva il listino nel db
     * @param listinoGestito il listino da salvare
     */
    public void salvaListino(Listino listinoGestito) {
        this.listinoRepository.save(listinoGestito);
    }

    /**
     * Ottieni il listino dal db
     * @return il listino
     */
    public Listino getListino() {
        return this.listinoRepository.findAll().stream().findFirst().orElseThrow();
    }

    /**
     *
     * @param userName
     * @return
     */
    public User getUserByUserName(String userName) {
        return this.userRepository.findById(userName).stream().findFirst().orElse(null);
    }

    /**
     *
     * @param user
     * @return
     */
    public Collection<Prenotazione> getPrenotazioniUtente(User user){
        return this.prenotazioneRepository.findAll().stream()
                .filter(p -> Objects.equals(p.getUser().getUserName(), user.getUserName()))
                .toList();
    }

    /**
     * Ottieni la lista delle tipologie non utilizzate da nessun ombrellone
     * @return la lista delle tipologie o null se è vuota
     */
    public Collection<TipologiaOmbrellone> getListaTipologieOmbrelloneNonUtilizzate() {
        ArrayList<Ombrellone> listaOmbrelloni = this.getSpiaggia().getListaOmbrelloni();
        Collection<String> tipologieUtilizzate = listaOmbrelloni.stream()
                .filter(o -> o.getTipologia() != null)
                .map(o -> o.getTipologia().getNome()).distinct().toList();
        return this.tipologiaOmbrelloneRepository.findAll().stream()
                .filter(tipologiaOmbrellone -> tipologieUtilizzate.stream()
                        .noneMatch(t -> t.equals(tipologiaOmbrellone.getNome()))).toList();
    }

    public Collection<Ombrellone> getOmbrelloniConPrenotazione() {
        Collection<Prenotazione> prenotazioni = this.prenotazioneRepository.findAll();
        return prenotazioni.stream().map(Prenotazione::getOmbrellone).distinct().toList();
    }

    public Collection<Ombrellone> getOmbrelloniPrenotatiInData(Date date) {
        return this.prenotazioneRepository.findAll().stream()
                .filter(p -> p.getDataInMillis() == date.getTime())
                .map(Prenotazione::getOmbrellone)
                .toList();
    }

    public void salvaPrenotazione(Prenotazione prenotazione) {
        this.prenotazioneRepository.save(prenotazione);
    }
}