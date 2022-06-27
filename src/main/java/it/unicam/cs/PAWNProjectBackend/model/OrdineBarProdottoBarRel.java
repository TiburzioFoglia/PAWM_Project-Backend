package it.unicam.cs.PAWNProjectBackend.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.neo4j.core.schema.RelationshipId;
import org.springframework.data.neo4j.core.schema.RelationshipProperties;
import org.springframework.data.neo4j.core.schema.TargetNode;

@RelationshipProperties
@Data
@NoArgsConstructor
public class OrdineBarProdottoBarRel {
    @RelationshipId
    private Long id;

    @TargetNode
    private ProdottoBar prodottoBar;

    private Integer quantita;

    public OrdineBarProdottoBarRel(ProdottoBar prodottoBar) {
        this.prodottoBar = prodottoBar;
    }

    public OrdineBarProdottoBarRel(ProdottoBar prodottoBar, Integer quantita) {
        this.prodottoBar = prodottoBar;
        this.quantita = quantita;
    }
}
