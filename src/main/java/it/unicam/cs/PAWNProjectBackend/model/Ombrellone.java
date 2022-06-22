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
    private String nomeTipo;
    private int numeroLettiniAssociati;
    private boolean prenotato;

    @Relationship(type = "HAS_COORDINATES",direction = Relationship.Direction.OUTGOING)
    private Coordinate location;

    public Ombrellone(String nomeTipo, Coordinate location){
        this.location = location;
        this.nomeTipo = nomeTipo;
        this.numeroLettiniAssociati = 0;
        this.prenotato = false;
    }
}



