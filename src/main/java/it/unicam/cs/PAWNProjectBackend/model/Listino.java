package it.unicam.cs.PAWNProjectBackend.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
/*

@Data
@NoArgsConstructor
@Node
public class Listino {

    @Id
    @GeneratedValue
    private Long id;

    @Relationship(type = "PREZZO_TIPOLOGIA",direction = Relationship.Direction.OUTGOING)
    private Collection<ListinoTipologiaOmbrelloneRel> prezziTipologia;

    @Relationship(type = "PREZZO_PRODOTTOBAR",direction = Relationship.Direction.OUTGOING)
    private Collection<ListinoProdottoBarRel> prezziBar;

    @Relationship(type = "PREZZO_FASCIA",direction = Relationship.Direction.OUTGOING)
    private Collection<ListinoFasciaDiPrezzoRel> prezziFascia;

    private double prezzoBaseOmbrellone;
    private double prezzoBaseLettino;

    public double calcolaPrezzoPrenotazione(int idTipologia, String nomeFascia) {
        Double prezzoFascia = 0.0;
        for(ListinoFasciaDiPrezzoRel listinoFasciaDiPrezzoRel : this.prezziFascia.stream().toList()){
            if(listinoFasciaDiPrezzoRel.getFasciaDiPrezzo().getNome().equals(nomeFascia))
                prezzoFascia = listinoFasciaDiPrezzoRel.getPrezzo();
        }
        for(ListinoTipologiaOmbrelloneRel listinoTipologiaOmbrelloneRel : this.prezziTipologia.stream().toList()){
            if(listinoTipologiaOmbrelloneRel.getTipologiaOmbrellone().getId() == idTipologia)
                return prezzoBaseOmbrellone * listinoTipologiaOmbrelloneRel.getPrezzo() * prezzoFascia;
        }

        System.out.println("La tipologia su cui calcolare il prezzo della prenotazione non esiste");
        return -1;
    }


    public boolean controlloProdottoEsistente(ProdottoBar prodottoBar) {
        return this.prezziBar.stream().map(ListinoProdottoBarRel::getProdottoBar).toList().contains(prodottoBar);
    }

    public void aggiungiAllaListaProdotti(ProdottoBar nuovoProdottoBar, Double prezzoProdotto) {
        this.prezziBar.add(new ListinoProdottoBarRel(nuovoProdottoBar,prezzoProdotto));
    }

    public Boolean eliminaProdotto(ProdottoBar prodottoScelto) {
        ListinoProdottoBarRel listinoProdottoBarRel;
        listinoProdottoBarRel = this.prezziBar.stream().filter(a -> a.getProdottoBar().equals(prodottoScelto))
                .findFirst().orElseThrow();
        return this.prezziBar.remove(listinoProdottoBarRel);
    }

    public void aggiornaTipologie(Collection<ListinoTipologiaOmbrelloneRel> tipologie) {
        this.prezziTipologia = tipologie;
    }

    public void aggiornaMappaFasce(Collection<ListinoFasciaDiPrezzoRel> fascieDiPrezzo) {
        this.prezziFascia = fascieDiPrezzo;
    }

    public void aggiungiTipologia(TipologiaOmbrellone tipologiaOmbrellone) {
        this.prezziTipologia.add(new ListinoTipologiaOmbrelloneRel(tipologiaOmbrellone,null));
    }

    public void aggiornaPrezzoProdotto(ProdottoBar prodottoScelto, double nuovoPrezzo) {
        this.prezziBar.stream().filter(a -> a.getProdottoBar().equals(prodottoScelto))
                .findFirst().orElseThrow().setPrezzo(nuovoPrezzo);
    }

    public void aggiornaDescrizioneProdotto(ProdottoBar prodottoScelto, String descrizione) {
        this.prezziBar.stream().filter(a -> a.getProdottoBar().equals(prodottoScelto))
                .findFirst().orElseThrow().getProdottoBar().setDescrizione(descrizione);
    }

    public boolean aggiornaNomeProdotto(ProdottoBar prodottoScelto, String nuovoNome) {
        if(this.prezziBar.stream().map(a -> a.getProdottoBar().getNomeProdotto().equals(nuovoNome)).toList().contains(true)){
            return false;
        }
        this.prezziBar.stream().filter(a -> a.getProdottoBar().equals(prodottoScelto))
                .findFirst().orElseThrow().getProdottoBar().setNomeProdotto(nuovoNome);
        return true;
    }

    public HashMap<TipologiaOmbrellone, Double> getPrezziTipologia() {
        HashMap<TipologiaOmbrellone, Double> prezziTipologiaMappa = new HashMap<>();
        for(ListinoTipologiaOmbrelloneRel listinoTipologiaOmbrelloneRel : this.prezziTipologia.stream().toList()){
            prezziTipologiaMappa.put(listinoTipologiaOmbrelloneRel.getTipologiaOmbrellone(),listinoTipologiaOmbrelloneRel.getPrezzo());
        }
        return prezziTipologiaMappa;
    }

    public void setNuovoPrezzoTipologia(String nomeTipologia, double nuovoPrezzo){
        this.prezziTipologia.stream().filter(a -> a.getTipologiaOmbrellone().getNome().equals(nomeTipologia))
                .findFirst().orElseThrow().setPrezzo(nuovoPrezzo);
    }

    public HashMap<ProdottoBar, Double> getPrezziBar() {
        HashMap<ProdottoBar, Double> prezziBarMappa = new HashMap<>();
        for(ListinoProdottoBarRel listinoProdottoBarRel : this.prezziBar.stream().toList()){
            prezziBarMappa.put(listinoProdottoBarRel.getProdottoBar(),listinoProdottoBarRel.getPrezzo());
        }
        return prezziBarMappa;
    }

    public HashMap<FasciaDiPrezzo, Double> getPrezziFascia() {
        HashMap<FasciaDiPrezzo, Double> prezziFasciaMappa = new HashMap<>();
        for(ListinoFasciaDiPrezzoRel listinoFasciaDiPrezzoRel : this.prezziFascia.stream().toList()){
            prezziFasciaMappa.put(listinoFasciaDiPrezzoRel.getFasciaDiPrezzo(),listinoFasciaDiPrezzoRel.getPrezzo());
        }
        return prezziFasciaMappa;
    }

    public void eliminaFasciaDiPrezzo(FasciaDiPrezzo fasciaDaModificare) {
        ListinoFasciaDiPrezzoRel listinoFasciaDiPrezzoRel;
        listinoFasciaDiPrezzoRel = this.prezziFascia.stream().filter(a -> a.getFasciaDiPrezzo().equals(fasciaDaModificare))
                .findFirst().orElseThrow();
        this.prezziFascia.remove(listinoFasciaDiPrezzoRel);
    }

    public void modificaPrezzoFascia(FasciaDiPrezzo fasciaDaModificare, double nuovoPrezzo) {
        this.prezziFascia.stream().filter(a -> a.getFasciaDiPrezzo().equals(fasciaDaModificare))
                .findFirst().orElseThrow().setPrezzo(nuovoPrezzo);
    }

    public void modificaLocazioniFascia(FasciaDiPrezzo fasciaDaModificare, FasciaDiPrezzo fasciaTemporanea) {
        FasciaDiPrezzo fascia = this.prezziFascia.stream().filter(a -> a.getFasciaDiPrezzo().equals(fasciaDaModificare))
                .findFirst().orElseThrow().getFasciaDiPrezzo();
        fascia.setCoordinateInizio(fasciaTemporanea.getCoordinateInizio());
        fascia.setCoordinateFine(fasciaTemporanea.getCoordinateFine());
    }

    public void addPrezziFascia(FasciaDiPrezzo fasciaDaAggiungere, double prezzoDaAggiungere) {
        this.prezziFascia.stream().filter(a -> a.getFasciaDiPrezzo().equals(fasciaDaAggiungere))
                .findFirst().orElseThrow().setPrezzo(prezzoDaAggiungere);
    }

    public boolean isNomeDisponibile(String nomeStringa) {
        if(this.prezziFascia.stream().map(a -> a.getFasciaDiPrezzo().getNome()).toList().contains(nomeStringa)){
            System.out.println("Il nome inserito non è disponibile");
            return false;
        }
        return true;
    }

    public ArrayList<TipologiaOmbrellone> getTipologie() {
        return new ArrayList<>(this.prezziTipologia.stream().map(ListinoTipologiaOmbrelloneRel::getTipologiaOmbrellone).toList());
    }

}*/
