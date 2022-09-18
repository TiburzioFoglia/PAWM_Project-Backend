package it.unicam.cs.PAWNProjectBackend.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;


@Data
@NoArgsConstructor
@Node
public class Prenotazione {

    @Id
    @GeneratedValue
    private Long id;

    @Relationship(type = "Prenotato_da",direction = Relationship.Direction.OUTGOING)
    private User user;

    private Long dataInMillis;

    private Integer numeroLettini;

    private double prezzoTotale;

    @Relationship(type = "Ombrellone_prenotato",direction = Relationship.Direction.OUTGOING)
    private Ombrellone ombrellone;


    public Prenotazione(User user, Ombrellone ombrellone, Integer numeroLettini, Date date) {
        this.user = user;
        this.dataInMillis = date.getTime();
        this.numeroLettini = numeroLettini;
        this.ombrellone = ombrellone;
    }
}
