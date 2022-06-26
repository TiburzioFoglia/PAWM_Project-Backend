package it.unicam.cs.PAWNProjectBackend.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

import java.util.ArrayList;
import java.util.Date;

@Data
@NoArgsConstructor
@Node
public class ListaAttivita {

    @Id
    @GeneratedValue
    private Long id;

    @Relationship(type = "CONTIENE_ATTIVITA",direction = Relationship.Direction.OUTGOING)
    private ArrayList<Attivita> listaAttivita;

    public boolean isNuovaAttivita(Attivita attivita){
        for(Attivita attivitaEsistenti : this.listaAttivita){
            if(attivitaEsistenti.equals(attivita)) return false;
        }
        return true;
    }

    public void aggiungiAttivita(Attivita attivita){
        this.listaAttivita.add(attivita);
    }

    public boolean controlloDisponibilitaNome(String nome, Date data, int fasciaOraria) {
        return !this.listaAttivita.stream().filter(a -> a.getData().equals(data) && a.getFasciaOraria() == fasciaOraria)
                .map(Attivita::getNome).toList().contains(nome);
    }

    public void aggiornaNomeAttivita(Attivita attivitaDaModificare, String nome) {
        this.listaAttivita.get(this.listaAttivita.indexOf(attivitaDaModificare)).setNome(nome);
    }

    public void aggiornaDescrizioneAttivita(Attivita attivitaDaModificare, String descrizione) {
        this.listaAttivita.get(this.listaAttivita.indexOf(attivitaDaModificare)).setDescrizione(descrizione);

    }

    public void aggiornaDataEOraAttivita(Attivita attivitaDaModificare, Date nuovaData, int fasciaOraria) {
        this.listaAttivita.get(this.listaAttivita.indexOf(attivitaDaModificare)).setData(nuovaData);
        this.listaAttivita.get(this.listaAttivita.indexOf(attivitaDaModificare)).setFasciaOraria(fasciaOraria);
    }

    public void aggiornaNumeroMassimoPostiAttivita(Attivita attivitaDaModificare, int nuovoMaxPosti) {
        this.listaAttivita.get(this.listaAttivita.indexOf(attivitaDaModificare)).setMaxPartecipanti(nuovoMaxPosti);

    }

    //TODO capire cosa ci fa questo qui
    public int ottieniNumeroIscrittiAttivita(Attivita attivitaDaModificare) {
        return attivitaDaModificare.getNumeroIscritti();
    }

    public void rimuoviAttivita(Attivita attivitaDaModificare) {
        this.listaAttivita.remove(attivitaDaModificare);
    }
}