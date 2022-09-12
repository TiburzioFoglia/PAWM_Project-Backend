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


@Data
@NoArgsConstructor
@Node
public class Listino {

    @Id
    @GeneratedValue
    private Long id;

    @Relationship(type = "PREZZO_TIPOLOGIA",direction = Relationship.Direction.OUTGOING)
    private Collection<ListinoTipologiaOmbrelloneRel> prezziTipologia;

    @Relationship(type = "PREZZO_FASCIA",direction = Relationship.Direction.OUTGOING)
    private Collection<ListinoFasciaDiPrezzoRel> prezziFascia;

    private double prezzoBaseOmbrellone;
    private double prezzoBaseLettino;






    /*public double calcolaPrezzoPrenotazione(int idTipologia, String nomeFascia) {
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
    }*/


    /*public HashMap<TipologiaOmbrellone, Double> getPrezziTipologia() {
        HashMap<TipologiaOmbrellone, Double> prezziTipologiaMappa = new HashMap<>();
        for(ListinoTipologiaOmbrelloneRel listinoTipologiaOmbrelloneRel : this.prezziTipologia.stream().toList()){
            prezziTipologiaMappa.put(listinoTipologiaOmbrelloneRel.getTipologiaOmbrellone(),listinoTipologiaOmbrelloneRel.getPrezzo());
        }
        return prezziTipologiaMappa;
    }*/

    /*public void setNuovoPrezzoTipologia(String nomeTipologia, double nuovoPrezzo){
        this.prezziTipologia.stream().filter(a -> a.getTipologiaOmbrellone().getNome().equals(nomeTipologia))
                .findFirst().orElseThrow().setPrezzo(nuovoPrezzo);
    }*/



    /*public HashMap<FasciaDiPrezzo, Double> getPrezziFascia() {
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
    }*/

}
