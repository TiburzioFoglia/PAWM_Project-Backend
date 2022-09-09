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
/*

@Data
@NoArgsConstructor
@Node
public class Prenotazione {

    @Id
    @GeneratedValue
    private Long id;
    private Date dataInizio;
    private Date dataFine;
    private int idCliente; //TODO
    private double prezzoTotale;

    @Relationship(type = "FASCIA_ORARIA_DATA_ASSOCIATA",direction = Relationship.Direction.OUTGOING)
    private Collection<PrenotazioneDateFasciaOrariaOmbrelloneRel> mappaDateFasceOmbrelloni;

    public Prenotazione(Date dataInizio, Date dataFine, int idCliente){
        this.mappaDateFasceOmbrelloni = new ArrayList<>();
        this.dataInizio = dataInizio;
        this.dataFine = dataFine;
        this.idCliente = idCliente;
    }

    public Prenotazione(Date dataInizio, Date dataFine, int idCliente, double prezzoTotale) {
        this.dataInizio = dataInizio;
        this.dataFine = dataFine;
        this.idCliente = idCliente;
        this.prezzoTotale = prezzoTotale;
        this.mappaDateFasceOmbrelloni = new ArrayList<>();
    }


}*/
