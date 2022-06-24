package it.unicam.cs.PAWNProjectBackend.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.neo4j.core.schema.RelationshipId;
import org.springframework.data.neo4j.core.schema.RelationshipProperties;
import org.springframework.data.neo4j.core.schema.TargetNode;

@RelationshipProperties
@Data
@NoArgsConstructor
public class AttivitaAttrezzaturaRel {
    @RelationshipId
    private Long id;

    @TargetNode
    private Attrezzatura attrezzaturaNode;

    private Integer quantita;

    public AttivitaAttrezzaturaRel(Attrezzatura attrezzaturaNode) {
        this.attrezzaturaNode = attrezzaturaNode;
    }
}
