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
public class Spiaggia {

    @Id
    @GeneratedValue
    private Long id;

    @Relationship(type = "CONTIENE",direction = Relationship.Direction.OUTGOING)
    private ArrayList<Ombrellone> listaOmbrelloni;

    private int totaleOmbrelloni;

    /**
     * Elimina un ombrellone dalla spiaggia settando il suo tipo a null
     * @param ombrellone l'ombrellone da eliminare
     */
    public void deleteOmbrellone(Ombrellone ombrellone) {
        ombrellone.setTipologia(null);
        totaleOmbrelloni--;
    }

    /**
     * Ottieni l'ombrellone al posto indicato dalle coordinate
     * @param location le coordinate
     * @return l'ombrellone cercato
     */
    public Ombrellone getOmbrelloneAtLocation(Coordinate location) {
        return this.listaOmbrelloni.stream()
                .filter(a -> a.getLocation().getXAxis() == location.getXAxis()
                        && a.getLocation().getYAxis() == location.getYAxis()).findFirst().orElseThrow();
    }


    /**
     * Ottieni l'ombrellone con id passato
     * @param id l'id dell'ombrellone
     * @return l'ombrellone
     */
    public Ombrellone getOmbrelloneById(Long id) {
        return this.listaOmbrelloni.stream().filter(a -> a.getId().equals(id)).findFirst().orElseThrow();
    }

    /**
     * Questo metodo controlla la lista ombrelloni della spiaggia e conteggia il numero di ombrelloni presenti
     */
    public void aggiornaTotaleOmbrelloni() {
        int totaleOmbrelloni = 0;
        for(Ombrellone ombrellone : this.listaOmbrelloni) if(ombrellone.getTipologia() != null) totaleOmbrelloni++;
        this.totaleOmbrelloni = totaleOmbrelloni;

    }


}