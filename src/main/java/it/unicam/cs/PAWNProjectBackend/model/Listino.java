package it.unicam.cs.PAWNProjectBackend.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Data
@NoArgsConstructor
@Node
public class Listino {

    @Id
    @GeneratedValue
    private Long id;

    //TODO controllare e far diventare collegamento (con oggetto relazione)
    private HashMap<TipologiaOmbrellone, Double> prezziTipologia;
    //TODO controllare e far diventare collegamento (con oggetto relazione)
    private HashMap<ProdottoBar, Double> prezziBar;
    //TODO controllare e far diventare collegamento (con oggetto relazione)
    private HashMap<FasciaDiPrezzo, Double> prezziFascia;
    private double prezzoBaseOmbrellone;
    private double prezzoBaseLettino;

    public double calcolaPrezzoPrenotazione(int idTipologia, String nomeFascia) {
        FasciaDiPrezzo fascia = null;
        for(FasciaDiPrezzo fasciaCorrente : this.prezziFascia.keySet())
            if(fasciaCorrente.getNome().equals(nomeFascia))
                fascia = fasciaCorrente;
        for(TipologiaOmbrellone tipologia : this.prezziTipologia.keySet())
            if(tipologia.getId() == idTipologia)
                return prezzoBaseOmbrellone * prezziTipologia.get(tipologia) * prezziFascia.get(fascia) ;
        System.out.println("La tipologia su cui calcolare il prezzo della prenotazione non esiste");
        return -1;
    }


    public boolean controlloProdottoEsistente(ProdottoBar prodottoBar) {
        return this.prezziBar.containsKey(prodottoBar);
    }

    public void aggiungiAllaListaProdotti(ProdottoBar nuovoProdottoBar, Double prezzoProdotto) {
        this.prezziBar.put(nuovoProdottoBar, prezzoProdotto);
    }

    public Double eliminaProdotto(ProdottoBar prodottoScelto) {
        return this.getPrezziBar().remove(prodottoScelto);
    }

    public void aggiornaTipologie(HashMap<TipologiaOmbrellone, Double> tipologie) {
        this.prezziTipologia = tipologie;
    }

    public void aggiornaMappaFasce(HashMap<FasciaDiPrezzo, Double> fascieDiPrezzo) {
        this.prezziFascia = fascieDiPrezzo;
    }

    public void aggiungiTipologia(TipologiaOmbrellone tipologiaOmbrellone) {
        this.prezziTipologia.put(tipologiaOmbrellone,null);
    }

    public void aggiornaPrezzoProdotto(ProdottoBar prodottoScelto, double nuovoPrezzo) {
        this.getPrezziBar().put(prodottoScelto,nuovoPrezzo);
    }

    public void aggiornaDescrizioneProdotto(ProdottoBar prodottoScelto, String descrizione) {
        for(ProdottoBar prodotto: this.getPrezziBar().keySet()){
            if(prodotto.equals(prodottoScelto)) prodotto.setDescrizione(descrizione);
        }
    }

    public boolean aggiornaNomeProdotto(ProdottoBar prodottoScelto, String nuovoNome) {
        Double temp = this.prezziBar.get(prodottoScelto);
        for(ProdottoBar prodotto: this.getPrezziBar().keySet()){
            if(prodotto.getNomeProdotto().equals(nuovoNome)) return false;
        }
        this.prezziBar.remove(prodottoScelto);
        prodottoScelto.setNomeProdotto(nuovoNome);
        this.prezziBar.put(prodottoScelto,temp);
        return true;
    }

    public HashMap<TipologiaOmbrellone, Double> getPrezziTipologia() {
        return prezziTipologia;
    }

    public void setNuovoPrezzoTipologia(String nomeTipologia, double nuovoPrezzo){
        for(TipologiaOmbrellone tipologia : this.getPrezziTipologia().keySet()){
            if(tipologia.getNome().equals(nomeTipologia))
                this.getPrezziTipologia().put(tipologia, nuovoPrezzo);
        }
    }

    public void setPrezziTipologia(HashMap<TipologiaOmbrellone, Double> prezziTipologia) {
        this.prezziTipologia = prezziTipologia;
    }

    public HashMap<ProdottoBar, Double> getPrezziBar() {
        return prezziBar;
    }

    public void setPrezziBar(HashMap<ProdottoBar, Double> prezziBar) {
        this.prezziBar = prezziBar;
    }

    public HashMap<FasciaDiPrezzo, Double> getPrezziFascia() {
        return prezziFascia;
    }

    public void setPrezziFascia(HashMap<FasciaDiPrezzo, Double> prezziFascia) {
        this.prezziFascia = prezziFascia;
    }

    public double getPrezzoBaseOmbrellone() {
        return prezzoBaseOmbrellone;
    }

    public void setPrezzoBaseOmbrellone(double prezzoBaseOmbrellone) {
        this.prezzoBaseOmbrellone = prezzoBaseOmbrellone;
    }

    public void eliminaFasciaDiPrezzo(FasciaDiPrezzo fasciaDaModificare) {
        this.prezziFascia.remove(fasciaDaModificare);
    }

    public void modificaPrezzoFascia(FasciaDiPrezzo fasciaDaModificare, double nuovoPrezzo) {
        this.prezziFascia.replace(fasciaDaModificare,nuovoPrezzo);
    }

    public void mostraFasceEPrezzi() {
        for (Map.Entry<FasciaDiPrezzo, Double> entry : this.prezziFascia.entrySet()) {
            System.out.println(("Fascia " + entry.getKey().getNome() + " - costo: " + entry.getValue() + "."));
        }
    }

    public void modificaLocazioniFascia(FasciaDiPrezzo fasciaDaModificare, FasciaDiPrezzo fasciaTemporanea) {
        double app = this.prezziFascia.get(fasciaDaModificare);
        this.prezziFascia.remove(fasciaDaModificare);
        fasciaDaModificare.setCoordinateInizio(fasciaTemporanea.getCoordinateInizio());
        fasciaDaModificare.setCoordinateFine(fasciaTemporanea.getCoordinateFine());
        this.prezziFascia.put(fasciaDaModificare,app);
    }

    public void outputListaTipologie(){
        if(this.prezziTipologia.isEmpty()){
            System.out.println("Al momento non ci sono tipologie salvate");
        }
        else{
            System.out.println("Tipi: ");
            for (TipologiaOmbrellone tipologia: this.prezziTipologia.keySet()) {
                System.out.println(tipologia+" Moltipilicatore: "+ this.prezziTipologia.get(tipologia));
            }
        }
    }

    public void addPrezziFascia(FasciaDiPrezzo fasciaDaAggiungere, double prezzoDaAggiungere) {
        this.prezziFascia.put(fasciaDaAggiungere, prezzoDaAggiungere);
    }

    public boolean isNomeDisponibile(String nomeStringa) {
        for(FasciaDiPrezzo fascia : this.prezziFascia.keySet()){
            if(fascia.getNome().equals(nomeStringa)) {
                System.out.println("Il nome inserito non è disponibile");
                return false;
            }
        }
        return true;
    }

    public ArrayList<TipologiaOmbrellone> getTipologie() {
        return new ArrayList<>(this.prezziTipologia.keySet());
    }

    public void aggiornaMappaProdotti(HashMap<ProdottoBar, Double> ottieniMappaProdottiBar) {
        this.prezziBar = ottieniMappaProdottiBar;
    }

}