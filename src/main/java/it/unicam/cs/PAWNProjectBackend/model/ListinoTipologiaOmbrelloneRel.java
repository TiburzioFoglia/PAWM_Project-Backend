package it.unicam.cs.PAWNProjectBackend.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.neo4j.core.schema.RelationshipId;
import org.springframework.data.neo4j.core.schema.RelationshipProperties;
import org.springframework.data.neo4j.core.schema.TargetNode;

@RelationshipProperties
@Data
@NoArgsConstructor
public class ListinoTipologiaOmbrelloneRel {
    @RelationshipId
    private Long id;

    @TargetNode
    private TipologiaOmbrellone tipologiaOmbrellone;

    private Double prezzo;

    public ListinoTipologiaOmbrelloneRel(TipologiaOmbrellone tipologiaOmbrellone) {
        this.tipologiaOmbrellone = tipologiaOmbrellone;
    }

    public ListinoTipologiaOmbrelloneRel(TipologiaOmbrellone tipologiaOmbrellone, Double prezzo) {
        this.tipologiaOmbrellone = tipologiaOmbrellone;
        this.prezzo = prezzo;
    }
}
