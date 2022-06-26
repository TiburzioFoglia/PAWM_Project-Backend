package it.unicam.cs.PAWNProjectBackend.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.neo4j.core.schema.RelationshipId;
import org.springframework.data.neo4j.core.schema.RelationshipProperties;
import org.springframework.data.neo4j.core.schema.TargetNode;

@RelationshipProperties
@Data
@NoArgsConstructor
public class ListaAttrezzatureAttrezzaturaRel {
    @RelationshipId
    private Long id;

    @TargetNode
    private Attrezzatura attrezzatura;

    private Integer quantita;

    public ListaAttrezzatureAttrezzaturaRel(Attrezzatura attrezzatura) {
        this.attrezzatura = attrezzatura;
    }

    public ListaAttrezzatureAttrezzaturaRel(Attrezzatura attrezzatura, Integer quantita) {
        this.attrezzatura = attrezzatura;
        this.quantita = quantita;
    }
}
