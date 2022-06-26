package it.unicam.cs.PAWNProjectBackend.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;
import java.util.*;


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

    @Relationship(type = "QUANTITA_ATTREZZATURA_ASSOCIATA",direction = Relationship.Direction.OUTGOING)
    private Collection<AttivitaAttrezzaturaRel> quantitaAttrezzatureAssociate;

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
        this.quantitaAttrezzatureAssociate = new ArrayList<>();
        this.nome = nome;
        this.descrizione = descrizione;
        this.data = data;
        this.oreDurata = oreDurata;
        this.maxPartecipanti = maxPartecipanti;
        this.animatore = animatore;
        this.fasciaOraria = fasciaOraria;
        this.numeroIscritti = 0;
    }

    public boolean isAttrezzaturaAssociata(String nomeAttrezzatura) {
        for(Attrezzatura attrezzatura : this.quantitaAttrezzatureAssociate.stream().map(AttivitaAttrezzaturaRel::getAttrezzaturaNode).toList())
            if(attrezzatura.getNome().equals(nomeAttrezzatura))
                return true;
        return false;
    }

    public void aggiornaPostiDisponibiliAttivita(int numeropartecipanti) {
        this.setNumeroIscritti(this.getNumeroIscritti()+numeropartecipanti);
    }
}