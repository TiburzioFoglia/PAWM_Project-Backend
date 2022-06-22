package it.unicam.cs.PAWNProjectBackend.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

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

    private HashMap<Date, Integer> mappaDateFasce; //TODO
    private HashMap<Date, ArrayList<Ombrellone>> mappaDateListaOmbrelloni; //TODO

    public Prenotazione(Date dataInizio, Date dataFine, int idCliente){
        this.mappaDateListaOmbrelloni = new HashMap<Date, ArrayList<Ombrellone>>();
        this.mappaDateFasce = new HashMap<Date, Integer>();
        this.dataInizio = dataInizio;
        this.dataFine = dataFine;
        this.idCliente = idCliente;
    }

    public Prenotazione(Date dataInizio, Date dataFine, int idCliente, double prezzoTotale) {
        this.dataInizio = dataInizio;
        this.dataFine = dataFine;
        this.idCliente = idCliente;
        this.prezzoTotale = prezzoTotale;
        this.mappaDateFasce = new HashMap<>();
        this.mappaDateListaOmbrelloni = new HashMap<>();
    }


}