package it.unicam.cs.PAWNProjectBackend.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

import java.util.*;

@Data
@NoArgsConstructor
@Node
public class ListaAttrezzatura {

    @Id
    @GeneratedValue
    private Long id;

    @Relationship(type = "QUANTITA_ATTREZZATURA_TOTALE",direction = Relationship.Direction.OUTGOING)
    private Collection<ListaAttrezzatureAttrezzaturaRel> quantitaAttrezzatureTotali;

    public HashMap<Attrezzatura,Integer> getMappaAttrezzatura() {
        HashMap<Attrezzatura, Integer> quantitaAttrezzatura = new HashMap<>();

        for(ListaAttrezzatureAttrezzaturaRel listaAttrezzatureAttrezzaturaRel : this.quantitaAttrezzatureTotali.stream().toList()){
            quantitaAttrezzatura.put(listaAttrezzatureAttrezzaturaRel.getAttrezzatura(),listaAttrezzatureAttrezzaturaRel.getQuantita());
        }
        return quantitaAttrezzatura;
    }

    public boolean riservaAttrezzatura(String nomeAttrezzatura, int numeroAttrezzatureDesiderato) {
        for(ListaAttrezzatureAttrezzaturaRel listaAttrezzatureAttrezzaturaRel : this.quantitaAttrezzatureTotali.stream().toList())
            if(Objects.equals(listaAttrezzatureAttrezzaturaRel.getAttrezzatura().getNome(), nomeAttrezzatura)) {
                listaAttrezzatureAttrezzaturaRel.setQuantita(listaAttrezzatureAttrezzaturaRel.getQuantita()-numeroAttrezzatureDesiderato);
                return true;
            }
        return false;
    }

    public boolean controlloAttrezzaturaEsistente(String nomeAttrezzatura) {
        for (ListaAttrezzatureAttrezzaturaRel listaAttrezzatureAttrezzaturaRel : this.quantitaAttrezzatureTotali.stream().toList()) {
            if (Objects.equals(listaAttrezzatureAttrezzaturaRel.getAttrezzatura().getNome(), nomeAttrezzatura)) {
                return true;
            }
        }
        return false;
    }

    public void addAttrezzatura(Attrezzatura nuovaAttrezzatura, int quantitaAttrezzatura) {
        this.quantitaAttrezzatureTotali.add(new ListaAttrezzatureAttrezzaturaRel(nuovaAttrezzatura,quantitaAttrezzatura));
    }

    public boolean controlloDisponibilitaAttrezzatura(String nomeAttrezzatura, int numeroDaRimuovere) {
        ListaAttrezzatureAttrezzaturaRel listaAttrezzatureAttrezzaturaRel = this.quantitaAttrezzatureTotali.stream().
                filter(a -> a.getAttrezzatura().getNome().equals(nomeAttrezzatura)).findFirst().orElseThrow();
        return (listaAttrezzatureAttrezzaturaRel.getQuantita() - numeroDaRimuovere) >= 0;
    }

    public void aggiungiQuantitaAttrezzatura(String nomeAttrezzatura, int quantitaAttrezzatura) {
        ListaAttrezzatureAttrezzaturaRel listaAttrezzatureAttrezzaturaRel = this.quantitaAttrezzatureTotali.stream().
                filter(a -> a.getAttrezzatura().getNome().equals(nomeAttrezzatura)).findFirst().orElseThrow();
        listaAttrezzatureAttrezzaturaRel.setQuantita(listaAttrezzatureAttrezzaturaRel.getQuantita() + quantitaAttrezzatura);
    }

    public void rimuoviQuantitaAttrezzatura(String attrezzatura, int quantitaAttrezzatura) {
        this.aggiungiQuantitaAttrezzatura(attrezzatura, -quantitaAttrezzatura);
    }
}