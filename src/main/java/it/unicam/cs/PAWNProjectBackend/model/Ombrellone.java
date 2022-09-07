package it.unicam.cs.PAWNProjectBackend.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

@Data
@NoArgsConstructor
@Node
public class Ombrellone {

    @Id
    @GeneratedValue
    private Long id;

    @Relationship(type = "HAS_TIPOLOGIA",direction = Relationship.Direction.OUTGOING)
    private TipologiaOmbrellone tipologia;

    @Relationship(type = "HAS_COORDINATE",direction = Relationship.Direction.OUTGOING)
    private Coordinate location;

    public Ombrellone(Coordinate location) {
        this.location = location;
        this.tipologia = null;
    }
}



