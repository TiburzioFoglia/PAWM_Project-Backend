package it.unicam.cs.PAWNProjectBackend.service;

import java.util.*;

import it.unicam.cs.PAWNProjectBackend.model.Coordinate;
import it.unicam.cs.PAWNProjectBackend.model.Ombrellone;
import it.unicam.cs.PAWNProjectBackend.model.Spiaggia;
import it.unicam.cs.PAWNProjectBackend.model.TipologiaOmbrellone;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class HandlerSpiaggia {

    private Spiaggia spiaggiaGestita;

    private final DBMSController dbmsController;
    private final HandlerListino handlerListinoAssociato;
    private HandlerPrenotazione handlerPrenotazioneAssociato;


    /**
     * Questo metodo serve ad aggiungere un ombrellone
     * @param tipo nome della tipologia
     * @param coordinateScelte coordinate in cui aggiungere l'ombrellone
     */
    public void aggiungiOmbrellone(String tipo , Coordinate coordinateScelte) {
        this.spiaggiaGestita = this.dbmsController.getSpiaggia();

        Ombrellone ombrellone = this.spiaggiaGestita.getOmbrelloneAtLocation(coordinateScelte);
        this.spiaggiaGestita.aggiungiOmbrellone(ombrellone,tipo);

        this.dbmsController.salvaOmbrellone(ombrellone);
        this.dbmsController.salvaSpiaggia(this.spiaggiaGestita);
    }


    /**
     * Questo metodo serve a modificare un ombrellone
     * @param id id dell'ombrellone da modificare
     * @param nomeTipo nome della tipologia, null se non la si vuole modificare
     * @param coordinate coordinate in cui spostare l'ombrellone, null se non la si vuole modificare
     */
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


    /**
     * Questo metodo serve ad aggiungere una griglia alla spiaggia
     * @param griglia la griglia da aggiungere
     */
    public void aggiungiGrigliaSpiaggia(Map<String, Integer> griglia){
        ArrayList<Ombrellone> listaOmbrelloni =  new ArrayList<>();

        for(int i=0 ; i<griglia.keySet().size() ; i++){
            StringBuilder s = new StringBuilder("riga");
            s.append(i);
            for(int j=0; j<griglia.get(s); j++){
                Coordinate coordinate = new Coordinate(j,i);
                Ombrellone ombrellone = new Ombrellone(coordinate);
                this.dbmsController.salvaOmbrellone(ombrellone);
                listaOmbrelloni.add(ombrellone);
            }
        }
        this.spiaggiaGestita.setListaOmbrelloni(listaOmbrelloni);
        this.dbmsController.salvaSpiaggia(this.spiaggiaGestita);
    }

    public void modificaGrigliaSpiaggia(){ //TODO in fase di modifica
        this.spiaggiaGestita = this.dbmsController.getSpiaggia();


        this.sceltaModificaGriglia(listaOmbrelloni);



        this.dbmsController.salvaSpiaggia(this.spiaggiaGestita);
    }




    /**
     * Questo metodo serve ad aggiungere una tipologia di ombrellone
     */
    public void aggiungiTipologiaOmbrellone() {
        HashMap<TipologiaOmbrellone, Double> tipologie = this.handlerListinoAssociato.ottieniPrezziTipologie();
        this.handlerListinoAssociato.getListinoGestito().outputListaTipologie();

        boolean flag;
        do{
            this.inserisciInformazioniTipologia(tipologie);

            tipologie = this.handlerListinoAssociato.getListinoGestito().getPrezziTipologia();
            this.handlerListinoAssociato.getListinoGestito().outputListaTipologie();

            System.out.println("Vuoi aggiungere altre tipologie? [y/n] ");
            flag = Objects.equals(this.sc.nextLine().trim().toLowerCase(Locale.ROOT), "y");
        }while(flag);

        if(this.confermaOperazione()){
            if(this.dbmsController.aggiornaMappaTipologie(this.handlerListinoAssociato.getListinoGestito().getPrezziTipologia())){
                System.out.println("Operazione eseguita con successo");
            }
            else System.out.println("Operazioni fallita");
        }
        else System.out.println("Operazioni annullate");
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


    private void sceltaModificaGriglia(ArrayList<ArrayList<Ombrellone>> listaOmbrelloni) {
        System.out.println("Seleziona la riga che vuoi modificare (int)");
        int sceltaRiga = this.provaScannerInt();
        int sceltaOperazione = this.sceltaOperazioneModificaGriglia();
        if(sceltaOperazione == 1){
            this.allungamentoRiga(listaOmbrelloni.get(sceltaRiga));
        }
        if(sceltaOperazione == 2){
            this.accorciamentoRiga(listaOmbrelloni.get(sceltaRiga));
        }
        if(sceltaOperazione == 3){
            this.eliminazioneRiga(listaOmbrelloni,sceltaRiga);
        }
        if(sceltaOperazione == 4){
            this.aggiuntaRiga(sceltaRiga);
        }
        System.out.println(this.spiaggiaGestita.toString());
    }

    private void aggiuntaRiga(int sceltaRiga) {
        String direzione = "";
        boolean flag = true;
        while(flag){
            System.out.println("Scegli la direzione in cui aggiungere la riga [sopra/sotto]");
            direzione = this.sc.nextLine().trim().toLowerCase(Locale.ROOT);
            if(Objects.equals(direzione, "sopra") || Objects.equals(direzione, "sotto")) flag = false;
            else System.out.println("Cio' che hai inserito non e' accettabile, ritenta");
        }
        System.out.println("Scegli la lunghezza della nuova riga (int)");
        int lunghezzaNuovaRiga = this.provaScannerInt();

        this.spiaggiaGestita.aggiungiNuovaRiga(sceltaRiga,direzione,lunghezzaNuovaRiga);
    }

    private void accorciamentoRiga(ArrayList<Ombrellone> riga) {
        System.out.println("Scegli di quanto accorciare la riga (int)");
        int lunghezzaAccorciamento = this.provaScannerInt();
        String direzione = this.sceltaDirezioneOperazione("accorciare");

        if(lunghezzaAccorciamento >= riga.size()){
            System.out.println("Stai cercando di accorciare troppo la riga, l'operazione verrà annullata");
            return;
        }

        if(this.isParteRigaEmpty(riga,lunghezzaAccorciamento,direzione)){
            if(Objects.equals(direzione, "d")) for(int i=0;i<lunghezzaAccorciamento;i++) riga.remove(riga.get(riga.size()-1));
            else for(int i=0;i<lunghezzaAccorciamento;i++) riga.remove(0);
        }
        else System.out.println("Impossibile accorciare la riga perchè almeno un ombrellone è presente");

    }

    private boolean isParteRigaEmpty(ArrayList<Ombrellone> riga, int lunghezzaAccorciamento, String direzione){
        ArrayList<Ombrellone> parteRiga = new ArrayList<>();

        if(Objects.equals(direzione, "d"))
            for(int i=0;i<lunghezzaAccorciamento;i++) parteRiga.add(riga.get(riga.size()-1-i));
        else
            for(int i=0;i<lunghezzaAccorciamento;i++) parteRiga.add(riga.get(i));

        for(Ombrellone ombrellone : parteRiga){
            if(ombrellone != null) return false;
        }
        return true;
    }

    private void eliminazioneRiga(ArrayList<ArrayList<Ombrellone>> listaOmbrelloni, int sceltaRiga) {
        if(this.confermaOperazione()) {
            if (this.isRigaEmpty(listaOmbrelloni.get(sceltaRiga))) {
                this.spiaggiaGestita.eliminaRiga(sceltaRiga);
            }
            else System.out.println("La fila non può essere eliminata poichè contiene almeno un ombrellone");
        }
    }

    private boolean isRigaEmpty(ArrayList<Ombrellone> riga){
        for(Ombrellone ombrellone : riga){
            if(ombrellone != null) return false;
        }
        return true;
    }

    private void allungamentoRiga(ArrayList<Ombrellone> riga) {
        System.out.println("Scegli di quanto allungare la riga (int)");
        int lunghezzaAllungamento = this.provaScannerInt();
        String direzione = this.sceltaDirezioneOperazione("allungare");

        if(Objects.equals(direzione, "d")) for(int i=0;i<lunghezzaAllungamento;i++) riga.add(null);
        else for(int i=0;i<lunghezzaAllungamento;i++) riga.add(0,null);
    }

    private String sceltaDirezioneOperazione(String operazione){
        String direzione;
        while(true){
            System.out.println("Scegli la direzione in cui "+ operazione +" la riga [d/s]");
            direzione = this.sc.nextLine().trim().toLowerCase(Locale.ROOT);
            if(Objects.equals(direzione, "d") || Objects.equals(direzione, "s")) return direzione;
            else System.out.println("Cio' che hai inserito non e' accettabile, ritenta");
        }

    }

    private int sceltaOperazioneModificaGriglia(){
        do{
            System.out.println("Scegli l'operazione da eseguire (int)");
            System.out.println("1\tAllungare la riga\n2\tAccorciare la riga\n3\tEliminare la riga\n4\tAggiungere una riga ");
            int sceltaOperazione = this.provaScannerInt();
            if(sceltaOperazione==1 || sceltaOperazione==2 || sceltaOperazione==3 || sceltaOperazione==4) return sceltaOperazione;
            else{
                System.out.println("Il numero inserito non rappresenta un'operazione, ritenta");
            }
        }while(true);
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
    }
}
