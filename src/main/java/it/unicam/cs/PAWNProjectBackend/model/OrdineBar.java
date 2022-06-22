package it.unicam.cs.PAWNProjectBackend.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;

import java.util.HashMap;

@Data
@NoArgsConstructor
@Node
public class OrdineBar {

    @Id
    @GeneratedValue
    private Long id;
    private int idCliente;
    private HashMap<ProdottoBar, Integer> prodottiOrdinati;

    public OrdineBar(int idCliente, HashMap<ProdottoBar,Integer> prodottiOrdinati){
        this.idCliente = idCliente;
        this.prodottiOrdinati = prodottiOrdinati;
    }

    public int getIdCliente() {
        return idCliente;
    }

    public HashMap<ProdottoBar, Integer> getProdottiOrdinati() {
        return prodottiOrdinati;
    }

    public void printProdottiOrdine(){
        for(ProdottoBar currentProdotto : this.prodottiOrdinati.keySet())
            System.out.println(currentProdotto.getNomeProdotto() + ": " + this.prodottiOrdinati.get(currentProdotto) + "pz");
    }
}
