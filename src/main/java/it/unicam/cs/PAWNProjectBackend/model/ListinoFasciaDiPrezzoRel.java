package it.unicam.cs.PAWNProjectBackend.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.neo4j.core.schema.RelationshipId;
import org.springframework.data.neo4j.core.schema.RelationshipProperties;
import org.springframework.data.neo4j.core.schema.TargetNode;

@RelationshipProperties
@Data
@NoArgsConstructor
public class ListinoFasciaDiPrezzoRel {
    @RelationshipId
    private Long id;

    @TargetNode
    private FasciaDiPrezzo fasciaDiPrezzo;

    private Double prezzo;

    public ListinoFasciaDiPrezzoRel(FasciaDiPrezzo fasciaDiPrezzo) {
        this.fasciaDiPrezzo = fasciaDiPrezzo;
    }

    public ListinoFasciaDiPrezzoRel(FasciaDiPrezzo fasciaDiPrezzo, Double prezzo) {
        this.fasciaDiPrezzo = fasciaDiPrezzo;
        this.prezzo = prezzo;
    }
}
