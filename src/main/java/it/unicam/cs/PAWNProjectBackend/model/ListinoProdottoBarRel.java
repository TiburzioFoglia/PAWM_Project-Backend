package it.unicam.cs.PAWNProjectBackend.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.neo4j.core.schema.RelationshipId;
import org.springframework.data.neo4j.core.schema.RelationshipProperties;
import org.springframework.data.neo4j.core.schema.TargetNode;

@RelationshipProperties
@Data
@NoArgsConstructor
public class ListinoProdottoBarRel {
    @RelationshipId
    private Long id;

    @TargetNode
    private ProdottoBar prodottoBar;

    private Double prezzo;

    public ListinoProdottoBarRel(ProdottoBar prodottoBar) {
        this.prodottoBar = prodottoBar;
    }

    public ListinoProdottoBarRel(ProdottoBar prodottoBar, Double prezzo) {
        this.prodottoBar = prodottoBar;
        this.prezzo = prezzo;
    }
}
