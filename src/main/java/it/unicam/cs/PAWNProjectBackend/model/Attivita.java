package it.unicam.cs.PAWNProjectBackend.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Objects;

@Data
@NoArgsConstructor
@Node
public class Attivita {

    @Id
    @GeneratedValue
    private Long id;
    private Date data;
    private int fasciaOraria;
    private int numeroIscritti;
    private String nome;
    private String descrizione;
    private int maxPartecipanti;
    private String animatore;
    private int oreDurata;

    //TODO trasformare in collegamento
    private HashMap<Attrezzatura,Integer> attrezzatureAssociate;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Attivita attivita = (Attivita) o;
        return fasciaOraria == attivita.fasciaOraria && Objects.equals(data, attivita.data) && Objects.equals(nome, attivita.nome);
    }

    @Override
    public int hashCode() {
        return Objects.hash(data, fasciaOraria, nome);
    }

    public Attivita(String nome, String descrizione, Date data, int maxPartecipanti, String animatore, int oreDurata, int fasciaOraria){
        this.attrezzatureAssociate = new HashMap<>();
        this.nome = nome;
        this.descrizione = descrizione;
        this.data = data;
        this.oreDurata = oreDurata;
        this.maxPartecipanti = maxPartecipanti;
        this.animatore = animatore;
        this.fasciaOraria = fasciaOraria;
        this.numeroIscritti = 0;
    }

    public void printDettagliAttivita(){
        DateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");
        System.out.println(nome + ":\t" +
                formatter.format(data) + '\n' +
                descrizione + '\n' + "Numero massimo di partecipanti: " +
                maxPartecipanti + '\n' + "Numero posti disponibili: " +
                (maxPartecipanti - numeroIscritti) + '\n' +
                "Animatore: " + animatore + '\n' +
                "Durata (ore): " + oreDurata
        );
        printAttrezzatureAssociate();
        System.out.println();
    }

    public void printAttrezzatureAssociate(){
        System.out.println("Attrezzature associate:");
        for(Attrezzatura attrezzatura : this.attrezzatureAssociate.keySet()){
            System.out.println("Nome: " + attrezzatura.getNome() + "\t|\t" + this.attrezzatureAssociate.get(attrezzatura) + "pz");
        }
    }

    public boolean isAttrezzaturaAssociata(String nomeAttrezzatura) {
        for(Attrezzatura attrezzatura : this.attrezzatureAssociate.keySet())
            if(attrezzatura.getNome().equals(nomeAttrezzatura))
                return true;
        return false;
    }

    public void aggiornaPostiDisponibiliAttivita(int numeropartecipanti) {
        this.setNumeroIscritti(this.getNumeroIscritti()+numeropartecipanti);
    }
}