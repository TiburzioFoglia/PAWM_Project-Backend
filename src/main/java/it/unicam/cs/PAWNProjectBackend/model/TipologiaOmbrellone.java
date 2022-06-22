package it.unicam.cs.PAWNProjectBackend.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;

@Data
@NoArgsConstructor
@Node
public class TipologiaOmbrellone {

    @Id
    @GeneratedValue
    private Long id;
    private String nome;
    private String descrizione;

    public TipologiaOmbrellone (String nome, String descrizione){
        this.nome = nome;
        this.descrizione = descrizione;
    }

    @Override
    public String toString() {
        return "[" + nome + ": '" + descrizione + '\'' + ']';
    }

}