package it.unicam.cs.PAWNProjectBackend.service;

import java.util.*;

import it.unicam.cs.PAWNProjectBackend.model.Coordinate;
import it.unicam.cs.PAWNProjectBackend.model.Ombrellone;
import it.unicam.cs.PAWNProjectBackend.model.Spiaggia;
import it.unicam.cs.PAWNProjectBackend.model.TipologiaOmbrellone;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class HandlerSpiaggia {

    private Spiaggia spiaggiaGestita;
    private final DBMSController dbmsController;


    /**
     * Questo metodo serve ad aggiungere un ombrellone
     * @param tipo la tipologia dell'ombrellone
     * @param coordinateScelte coordinate in cui aggiungere l'ombrellone
     */
    public void aggiungiOmbrellone(TipologiaOmbrellone tipo , Coordinate coordinateScelte) {
        this.spiaggiaGestita = this.dbmsController.getSpiaggia();
        Ombrellone ombrellone = this.spiaggiaGestita.getOmbrelloneAtLocation(coordinateScelte);
        ombrellone.setTipologia(tipo);
        this.spiaggiaGestita.aggiornaTotaleOmbrelloni();
        log.info("totale ombrelloni: {}", this.spiaggiaGestita.getTotaleOmbrelloni());
        this.dbmsController.salvaOmbrellone(ombrellone);
        this.dbmsController.salvaSpiaggia(this.spiaggiaGestita);
    }

    /**
     * Elimina un ombrellone dalla spiaggia dato il suo id
     * @param id l'id dell'ombrellone
     */
    public void deleteOmbrelloneById(Long id) {
        this.spiaggiaGestita = this.dbmsController.getSpiaggia();
        Ombrellone ombrellone = this.spiaggiaGestita.getOmbrelloneById(id);
        log.info("ombrellone eliminato pre: {}", ombrellone);
        this.spiaggiaGestita.deleteOmbrellone(ombrellone);
        log.info("ombrellone eliminato post: {}", ombrellone);
        log.info("totale ombrelloni: {}", this.spiaggiaGestita.getTotaleOmbrelloni());
        this.dbmsController.salvaOmbrellone(ombrellone);
        this.dbmsController.salvaSpiaggia(this.spiaggiaGestita);
    }


    /**
     * Questo metodo serve ad aggiungere una griglia alla spiaggia
     * @param griglia la griglia da aggiungere
     */
    public void aggiungiGrigliaSpiaggia(ArrayList<Integer> griglia){
        ArrayList<Ombrellone> listaOmbrelloni =  new ArrayList<>();

        for(int i=0 ; i<griglia.size(); i++){
            for(int j=0; j<griglia.get(i); j++){
                Coordinate coordinate = new Coordinate(j,i);
                this.dbmsController.salvaCoordinate(coordinate);
                Ombrellone ombrellone = new Ombrellone(coordinate);
                this.dbmsController.salvaOmbrellone(ombrellone);
                listaOmbrelloni.add(ombrellone);
            }
        }
        this.spiaggiaGestita = new Spiaggia();
        this.spiaggiaGestita.setListaOmbrelloni(listaOmbrelloni);
        this.dbmsController.salvaSpiaggia(this.spiaggiaGestita);
    }

    /**
     * Questo metodo serve a modificare la griglia spiaggia in base alla nuova griglia
     * @param griglia la nuova griglia
     * @param listaIdOmbrelloni la disposizione degli ombrelloni nella nuova griglia
     */
    public void modificaGrigliaSpiaggia(ArrayList<Integer> griglia, ArrayList<Long> listaIdOmbrelloni){
        ArrayList<Ombrellone> listaOmbrelloniModificata =  new ArrayList<>();
        this.spiaggiaGestita = this.dbmsController.getSpiaggia();
        int contatoreTotale = 0;
        for(int i=0 ; i<griglia.size(); i++){
            for(int j=0; j<griglia.get(i); j++){
                if(listaIdOmbrelloni.get(contatoreTotale) == -1){
                    Coordinate coordinate = new Coordinate(j,i);
                    this.dbmsController.salvaCoordinate(coordinate);
                    Ombrellone ombrellone = new Ombrellone(coordinate);
                    this.dbmsController.salvaOmbrellone(ombrellone);
                    listaOmbrelloniModificata.add(ombrellone);
                }
                else{
                    log.info("Cerco ombrellone esistente con id: {}",listaIdOmbrelloni.get(contatoreTotale));
                    Ombrellone ombrelloneEsistente = this.spiaggiaGestita.getOmbrelloneById(listaIdOmbrelloni.get(contatoreTotale));
                    if(ombrelloneEsistente.getTipologia() == null) throw new IllegalArgumentException();
                    log.info("Ombrellone trovato pre modifica: {}",ombrelloneEsistente);
                    ombrelloneEsistente.getLocation().setXAxis(j);
                    ombrelloneEsistente.getLocation().setYAxis(i);
                    log.info("Ombrellone trovato post modifica: {}",ombrelloneEsistente);
                    listaOmbrelloniModificata.add(ombrelloneEsistente);
                }
                contatoreTotale++;
            }
        }
        this.deletePostiSenzaOmbrelloni(this.spiaggiaGestita.getListaOmbrelloni());
        this.spiaggiaGestita.setListaOmbrelloni(listaOmbrelloniModificata);
        this.spiaggiaGestita.aggiornaTotaleOmbrelloni();
        this.dbmsController.salvaSpiaggia(this.spiaggiaGestita);
    }

    private void deletePostiSenzaOmbrelloni(ArrayList<Ombrellone> listaOmbrelloni) {
        for(Ombrellone ombrellone : listaOmbrelloni){
            if(ombrellone.getTipologia() == null){
                this.dbmsController.deleteCoordinate(ombrellone.getLocation());
                this.dbmsController.deleteOmbrellone(ombrellone);
            }
        }
    }







    /*
    *//**
     * Questo metodo serve a modificare un ombrellone
     * @param id id dell'ombrellone da modificare
     * @param nomeTipo nome della tipologia, null se non la si vuole modificare
     * @param coordinate coordinate in cui spostare l'ombrellone, null se non la si vuole modificare
     *//*
    public void modificaOmbrellone(Long id , String nomeTipo, Coordinate coordinate){
        this.spiaggiaGestita = this.dbmsController.getSpiaggia();
        Ombrellone ombrellone = this.spiaggiaGestita.getOmbrelloneById(id);

        if(nomeTipo == null) this.spiaggiaGestita.rimuoviOmbrellone(ombrellone);
        else this.spiaggiaGestita.modificaTipologiaOmbrellone(ombrellone,nomeTipo);

        if(nomeTipo != null && (ombrellone.getLocation().getXAxis() != coordinate.getXAxis() || ombrellone.getLocation().getYAxis() != coordinate.getYAxis())){
            Ombrellone ombrelloneTarget = this.spiaggiaGestita.getOmbrelloneAtLocation(coordinate);
            if(ombrelloneTarget.getNomeTipo() ==  null) this.spiaggiaGestita.spostaOmbrellone(ombrellone,coordinate);
            else this.spiaggiaGestita.scambiaOmbrelloni(ombrellone,ombrelloneTarget);
            this.dbmsController.salvaOmbrellone(ombrelloneTarget);
        }
        this.dbmsController.salvaOmbrellone(ombrellone);
        this.dbmsController.salvaSpiaggia(this.spiaggiaGestita);
    }











    private void inserisciInformazioniTipologia(HashMap<TipologiaOmbrellone, Double> listaTipi) {

        System.out.println("Inserisci il nome della nuova tipologia: ");
        String nome = this.sc.nextLine();
        System.out.println("Inserisci descrizione della nuova tipologia: ");
        String info = this.sc.nextLine();

        ArrayList<TipologiaOmbrellone> tipologie = new ArrayList<>(listaTipi.keySet().stream().toList()); //TODO controllare se funziona
        if(!this.controlloPresenzaTipologiaInserita(tipologie,nome)){
            this.handlerListinoAssociato.aggiungiNuovaTipologia(new TipologiaOmbrellone(nome,info));
            System.out.println("La nuova tipologia è stata aggiunta");
        }
        else{
            System.out.println("La tipologia inserita è già presente!");
        }
    }

    private boolean controlloPresenzaTipologiaInserita(HashMap<TipologiaOmbrellone, Double> listaTipi , String nome){
        if(!listaTipi.isEmpty()){
            for (TipologiaOmbrellone tipologia: listaTipi.keySet()) {
                if(tipologia.getNome().equals(nome)) return true;
            }
        }
        return false;
    }


    private String richiestaTipologia(ArrayList<TipologiaOmbrellone> listaTipologieDisponibili) {
        System.out.println("Digitare uno tra i tipi disponibili per associarlo all'ombrellone selezionato: ");
        for (TipologiaOmbrellone tipologia: listaTipologieDisponibili) {
            System.out.println(tipologia);
        }
        String idTipologia;
        boolean flag = true;
        do{
            idTipologia = sc.nextLine();
            for(TipologiaOmbrellone tipologia : listaTipologieDisponibili){
                if(tipologia.getNome().equals(idTipologia))
                    flag = false;
            }
            if(flag)
                System.out.println("La tipologia inserita non è disponibile o non esiste, ritenta");
        }while(flag);
        return idTipologia;
    }

    private ArrayList<TipologiaOmbrellone> controlloTipologia(String tipologia){
        Set<TipologiaOmbrellone> tipologieDisponibili = new HashSet<>();
        TipologiaOmbrellone tempTipologie = null;
        for(TipologiaOmbrellone tipo : this.handlerListinoAssociato.getListinoGestito().getPrezziTipologia().keySet()){
            if(tipo.getNome().equals(tipologia)){
                tempTipologie = tipo;
            }
        }
        for(TipologiaOmbrellone tipo : this.handlerListinoAssociato.getListinoGestito().getPrezziTipologia().keySet()) {
            if(this.handlerListinoAssociato.getListinoGestito().getPrezziTipologia().get(tipo) >= this.handlerListinoAssociato.getListinoGestito().getPrezziTipologia().get(tempTipologie)){
                tipologieDisponibili.add(tipo);
            }
        }
        return new ArrayList<>(tipologieDisponibili);
    }

    private void aggiornaTipologiaOmbrellone(String tipologia, Ombrellone ombrelloneSelezionato){
        spiaggiaGestita.aggiornaTipologiaOmbrellone(ombrelloneSelezionato, tipologia);
        this.dbmsController.aggiornaTipologiaOmbrellone(ombrelloneSelezionato.getId(), tipologia);
    }




    public Ombrellone getOmbrellone(long idOmbrellone) {
        for(ArrayList<Ombrellone> currentRow: spiaggiaGestita.getListaOmbrelloni())
            for(Ombrellone ombrelloneCorrente : currentRow)
                if(ombrelloneCorrente == null)
                    continue;
                else{
                    if(ombrelloneCorrente.getId() == idOmbrellone)
                        return ombrelloneCorrente;
                }
        return null;
    }*/
}
