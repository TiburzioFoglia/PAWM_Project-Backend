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
public class Spiaggia {

    @Id
    @GeneratedValue
    private Long id;

    @Relationship(type = "CONTIENE",direction = Relationship.Direction.OUTGOING)
    private ArrayList<Ombrellone> listaOmbrelloni;

    private int numeroRighe;
    private int totaleOmbrelloni;

    public ArrayList<ArrayList<Ombrellone>> getListaOmbrelloni(){
        ArrayList<ArrayList<Ombrellone>> grigliaOmbrelloni = new ArrayList<>();
        for(int i = 0;i<numeroRighe;i++) grigliaOmbrelloni.add(new ArrayList<>());

        for(ArrayList<Ombrellone> riga : grigliaOmbrelloni){
            int indiceRiga = grigliaOmbrelloni.indexOf(riga);
            Collection<Ombrellone> ombrelloniRiga = this.listaOmbrelloni.stream()
                    .filter(a -> a.getLocation().getYAxis() == indiceRiga).toList();
            for(int i = 0;i<ombrelloniRiga.size();i++){
                int appoggio = i;
                riga.add(ombrelloniRiga.stream().filter(a -> a.getLocation().getXAxis() == appoggio).findFirst().orElseThrow());
            }
        }
        return grigliaOmbrelloni;
    }

    public boolean isLocationOccupied(Coordinate coordinate) {
        return this.listaOmbrelloni.stream()
                .filter(a -> a.getLocation().getXAxis() == coordinate.getXAxis()
                        && a.getLocation().getYAxis() == coordinate.getYAxis())
                .toList().get(0).getNomeTipo() != null;
    }

    public Ombrellone getOmbrelloneAtLocation(Coordinate location) {
        return this.listaOmbrelloni.stream()
                .filter(a -> a.getLocation().getXAxis() == location.getXAxis()
                        && a.getLocation().getYAxis() == location.getYAxis()).findFirst().orElseThrow();
    }

    public void scambiaOmbrelloni(Ombrellone primoOmbrellone, Ombrellone secondoOmbrellone) {
        Coordinate coordinatePrimoOmbrellone = primoOmbrellone.getLocation();
        Coordinate coordinateSecondoOmbrellone = secondoOmbrellone.getLocation();

        this.listaOmbrelloni.stream().filter(a -> a.equals(primoOmbrellone))
                .findFirst().orElseThrow().setLocation(coordinateSecondoOmbrellone);
        this.listaOmbrelloni.stream().filter(a -> a.equals(secondoOmbrellone))
                .findFirst().orElseThrow().setLocation(coordinatePrimoOmbrellone);
    }

    public void spostaOmbrellone(Ombrellone ombrellone, Coordinate nuoveCoordinate) {
        Coordinate appoggio = ombrellone.getLocation();
        this.listaOmbrelloni.remove(this.listaOmbrelloni.stream()
                .filter(a -> a.getLocation().equals(nuoveCoordinate)).findFirst().orElseThrow());
        this.listaOmbrelloni.stream().filter(a -> a.equals(ombrellone))
                .findFirst().orElseThrow().setLocation(nuoveCoordinate);
        this.listaOmbrelloni.add(new Ombrellone(appoggio));
    }

    public void aggiornaTipologiaOmbrellone(Ombrellone ombrellone, String tipologia) {
        this.listaOmbrelloni.stream().filter(a -> a.equals(ombrellone))
                .findFirst().orElseThrow().setNomeTipo(tipologia);
    }

    public void rimuoviOmbrellone(Ombrellone ombrellone){ //TODO fare il controllo prenotazione prima di entrare qui
        this.listaOmbrelloni.remove(ombrellone);
        this.listaOmbrelloni.add(new Ombrellone(ombrellone.getLocation()));
        this.totaleOmbrelloni--;
    }

    public void aggiungiOmbrellone(Ombrellone ombrellone){
        if(this.listaOmbrelloni.stream().filter(a -> a.getLocation().equals(ombrellone.getLocation()))
                .toList().size() > 0){
            this.listaOmbrelloni.remove(this.listaOmbrelloni.stream()
                    .filter(a -> a.getLocation().equals(ombrellone.getLocation())).findFirst().orElseThrow());
            this.listaOmbrelloni.add(ombrellone);
            this.totaleOmbrelloni++;
        }
        //TODO controllare nel caso dell'if falso il fatto che potrebbe non star venendo inserito all'interno della griglia
        //(non dovrebbe essere un problema perche c'e' il frontend
    }


    //TODO cambiare in base all'handler
    /*
    public Ombrellone getOmbrellone(int idOmbrellone) {
        for(ArrayList<Ombrellone> currentRow: listaOmbrelloni)
            for(Ombrellone ombrelloneCorrente : currentRow)
                if(ombrelloneCorrente == null)
                    continue;
                else{
                    if(ombrelloneCorrente.getId() == idOmbrellone)
                        return ombrelloneCorrente;
                }
        return null;
    }
    */

    public ArrayList<Coordinate> ottieniPostiSenzaOmbrelloni(){

        ArrayList<Coordinate> coordinate = new ArrayList<>();
        int x = 0;
        int y = 0;

        for (ArrayList<Ombrellone> riga : this.getListaOmbrelloni()) {
            for (Ombrellone ombrellone : riga) {
                if(ombrellone.getNomeTipo() == null){
                    coordinate.add(new Coordinate(x,y));
                }
                else coordinate.add(null);
                x++;
            }
            x=0;
            y++;
        }

        return coordinate;
    }

    public void aggiungiGrigliaSpiaggia(ArrayList<ArrayList<Ombrellone>> grigliaSpiaggia) {
        ArrayList<Ombrellone> listaOmbrelloniSpiaggia = new ArrayList<>();
        for(ArrayList<Ombrellone> riga : grigliaSpiaggia){
            listaOmbrelloniSpiaggia.addAll(riga);
        }
        this.listaOmbrelloni = listaOmbrelloniSpiaggia;
    }

    //TODO controllare come sopra
    /*
    public boolean controlloEsistenzaOmbrellone(int idOmbrellone){
        for(ArrayList<Ombrellone> fila : this.listaOmbrelloni){
            for(Ombrellone ombrellone : fila){
                if(ombrellone != null)
                    if(ombrellone.getId() == idOmbrellone)
                        return true;
            }
        }
        return false;
    }
     */


    public void aggiornaSpiaggia(ArrayList<ArrayList<Ombrellone>> listaOmbrelloni) {
        int tempTotale = 0;
        ArrayList<Ombrellone> ombrelloni = new ArrayList<>();
        for(ArrayList<Ombrellone> riga : listaOmbrelloni)
            for(Ombrellone ombrellone : riga){
                if(ombrellone.getNomeTipo() != null) tempTotale++;
                ombrelloni.add(ombrellone);
            }
        this.totaleOmbrelloni = tempTotale;
        this.listaOmbrelloni = ombrelloni;
    }

    public void aggiungiNuovaRiga(int sceltaRiga, String direzione, int lunghezzaNuovaRiga) {
        ArrayList<Ombrellone> appoggio = new ArrayList<>();
        ArrayList<ArrayList<Ombrellone>> listaOmbrelloniAppoggio = this.getListaOmbrelloni();

        if(Objects.equals(direzione, "sopra")){
            for(int i = 0;i<lunghezzaNuovaRiga;i++) appoggio.add(new Ombrellone(new Coordinate(i,sceltaRiga)));
            listaOmbrelloniAppoggio.add(sceltaRiga, appoggio);
        }
        if(Objects.equals(direzione, "sotto")){
            for(int i = 0;i<lunghezzaNuovaRiga;i++) appoggio.add(new Ombrellone(new Coordinate(i,sceltaRiga+1)));

            if(listaOmbrelloniAppoggio.size()-1 == sceltaRiga) listaOmbrelloniAppoggio.add(appoggio);
            else{
                listaOmbrelloniAppoggio.add(sceltaRiga+1, appoggio);
            }
        }
        this.aggiornaCoordinateOmbrelloniSpiaggia(listaOmbrelloniAppoggio);
    }

    private void aggiornaCoordinateOmbrelloniSpiaggia(ArrayList<ArrayList<Ombrellone>> listaOmbrelloniAppoggio){
        int y=0;
        for(ArrayList<Ombrellone> riga: listaOmbrelloniAppoggio){
            int x = 0;
            for(Ombrellone ombrellone : riga){
                ombrellone.setLocation(new Coordinate(x,y));
                x++;
            }
            y++;
        }
        this.aggiornaSpiaggia(listaOmbrelloniAppoggio);
    }

    public void eliminaRiga(int sceltaRiga) {
        ArrayList<ArrayList<Ombrellone>> listaOmbrelloniAppoggio = this.getListaOmbrelloni();
        listaOmbrelloniAppoggio.remove(sceltaRiga);
        this.aggiornaCoordinateOmbrelloniSpiaggia(listaOmbrelloniAppoggio);
    }

}