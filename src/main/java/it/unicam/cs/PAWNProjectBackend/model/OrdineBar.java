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
public class OrdineBar {

    @Id
    @GeneratedValue
    private Long id;

    private int idCliente;

    @Relationship(type = "QUANTITA_PRODOTTOBAR_ASSOCIATA",direction = Relationship.Direction.OUTGOING)
    private Collection<OrdineBarProdottoBarRel>  prodottiOrdinati;

    public OrdineBar(int idCliente, HashMap<ProdottoBar,Integer> prodottiOrdinati){
        this.idCliente = idCliente;
        this.prodottiOrdinati = new ArrayList<>();
        for(ProdottoBar currentProdotto : prodottiOrdinati.keySet()){
            this.prodottiOrdinati.add(new OrdineBarProdottoBarRel(currentProdotto,prodottiOrdinati.get(currentProdotto)));
        }
    }

    public HashMap<ProdottoBar, Integer> getProdottiOrdinati() {
        HashMap<ProdottoBar, Integer> prodottiOrdinati = new HashMap<>();
        for(OrdineBarProdottoBarRel ordineBarProdottoBarRel : this.prodottiOrdinati.stream().toList()){
            prodottiOrdinati.put(ordineBarProdottoBarRel.getProdottoBar(),ordineBarProdottoBarRel.getQuantita());
        }
        return prodottiOrdinati;
    }
}
