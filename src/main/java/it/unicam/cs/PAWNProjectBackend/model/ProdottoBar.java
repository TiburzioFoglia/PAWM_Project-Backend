package it.unicam.cs.PAWNProjectBackend.model;


import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;

import java.util.Objects;

@Data
@NoArgsConstructor
@Node
public class ProdottoBar {

    @Id
    @GeneratedValue
    private Long id;
    private String descrizione;

    private String nomeProdotto;

    public ProdottoBar(String descrizione, String nomeProdotto){
        this.nomeProdotto = nomeProdotto;
        this.descrizione = descrizione;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProdottoBar that = (ProdottoBar) o;
        return id == that.id && Objects.equals(nomeProdotto, that.nomeProdotto);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nomeProdotto, id);
    }

}