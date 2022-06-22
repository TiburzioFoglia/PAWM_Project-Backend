package it.unicam.cs.PAWNProjectBackend.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Objects;

@Data
@NoArgsConstructor
@Node
public class Spiaggia {

    @Id
    @GeneratedValue
    private Long id;
    private ArrayList<ArrayList<Ombrellone>> listaOmbrelloni;
    private int totaleOmbrelloni;

    public ArrayList<ArrayList<Ombrellone>> getListaOmbrelloni(){
        return this.listaOmbrelloni;
    }

    public int getTotaleOmbrelloni(){
        return this.totaleOmbrelloni;
    }

    public boolean isLocationOccupied(Coordinate coordinate) {
        return listaOmbrelloni.get(coordinate.getYAxis()).get(coordinate.getXAxis()) != null;
    }

    public Ombrellone getOmbrelloneAtLocation(Coordinate location) {
        return listaOmbrelloni.get(location.getYAxis()).get(location.getXAxis());
    }

    public void scambiaOmbrelloni(Ombrellone primoOmbrellone, Ombrellone secondoOmbrellone) {
        int filaPrimoOmbrellone = primoOmbrellone.getLocation().getXAxis();
        int colonnaPrimoOmbrellone = primoOmbrellone.getLocation().getYAxis();
        int filaSecondoOmbrellone = secondoOmbrellone.getLocation().getXAxis();
        int colonnaSecondoOmbrellone = secondoOmbrellone.getLocation().getYAxis();
        listaOmbrelloni.get(filaPrimoOmbrellone).set(colonnaPrimoOmbrellone, secondoOmbrellone);
        listaOmbrelloni.get(filaSecondoOmbrellone).set(colonnaSecondoOmbrellone, primoOmbrellone);
        primoOmbrellone.setLocation(new Coordinate(filaSecondoOmbrellone,colonnaPrimoOmbrellone));
        secondoOmbrellone.setLocation(new Coordinate(filaSecondoOmbrellone,colonnaSecondoOmbrellone));
    }

    public void spostaOmbrellone(Ombrellone ombrellone, Coordinate nuoveCoordinate) {
        int filaOmbrellone = ombrellone.getLocation().getYAxis();
        int colonnaOmbrellone = ombrellone.getLocation().getXAxis();
        listaOmbrelloni.get(filaOmbrellone).set(colonnaOmbrellone, null);
        listaOmbrelloni.get(nuoveCoordinate.getYAxis()).set(nuoveCoordinate.getXAxis(), ombrellone);
        ombrellone.setLocation(nuoveCoordinate);
    }

    public void aggiornaTipologiaOmbrellone(Ombrellone ombrellone, String tipologia) {
        int filaOmbrellone = ombrellone.getLocation().getYAxis();
        int colonnaOmbrellone = ombrellone.getLocation().getXAxis();
        listaOmbrelloni.get(filaOmbrellone).get(colonnaOmbrellone).setNomeTipo(tipologia);
    }

    public boolean rimuoviOmbrellone(Ombrellone ombrellone){
        Ombrellone ombrelloneDaRimuovere;
        Ombrellone currentOmbrellone;
        for(ArrayList<Ombrellone> currentRow : listaOmbrelloni) {
            Iterator<Ombrellone>iter = currentRow.iterator();
            while(iter.hasNext()) {
                currentOmbrellone = iter.next();
                if(currentOmbrellone != null) {
                    if (currentOmbrellone.equals(ombrellone))
                        if (!currentOmbrellone.isPrenotato()) {
                            iter.remove();
                            totaleOmbrelloni--;
                            return true;
                        } else System.out.println("Ombrellone prenotato non rimuovibile");
                }
            }
        }
        return false;
    }

    public void aggiungiOmbrellone(Ombrellone ombrellone){
        int coordinataX = ombrellone.getLocation().getXAxis();
        int coordinataY = ombrellone.getLocation().getYAxis();
        if(coordinataY < this.listaOmbrelloni.size() && coordinataX < this.listaOmbrelloni.get(coordinataY).size() && coordinataX >= 0 && coordinataY >= 0) {
            this.listaOmbrelloni.get(coordinataY).set(coordinataX, ombrellone);
            totaleOmbrelloni++;
        }
        else System.out.println("Le coordinate inserite non rientrano nei limiti della griglia spiaggia, la scelta viene annullata");
    }

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


    public ArrayList<Coordinate> ottieniPostiSenzaOmbrelloni(){

        ArrayList<Coordinate> coordinate = new ArrayList<>();
        int x = 0;
        int y = 0;

        for (ArrayList<Ombrellone> riga : this.getListaOmbrelloni()) {
            for (Ombrellone ombrellone : riga) {
                if(ombrellone == null){
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
        this.listaOmbrelloni = grigliaSpiaggia;
    }

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

    public void aggiornaSpiaggia(ArrayList<ArrayList<Ombrellone>> listaOmbrelloni) {
        int tempTotale = 0;
        for(ArrayList<Ombrellone> riga : listaOmbrelloni)
            for(Ombrellone ombrellone : riga)
                if(ombrellone != null)
                    tempTotale++;
        this.totaleOmbrelloni = tempTotale;
        this.listaOmbrelloni = listaOmbrelloni;
    }

    public void aggiungiNuovaRiga(int sceltaRiga, String direzione, int lunghezzaNuovaRiga) {
        ArrayList<Ombrellone> appoggio = new ArrayList<>();
        for(int i = 0;i<lunghezzaNuovaRiga;i++){
            appoggio.add(null);
        }

        if(Objects.equals(direzione, "sopra")){
            this.listaOmbrelloni.add(sceltaRiga, appoggio);
        }
        if(Objects.equals(direzione, "sotto")){
            if(this.listaOmbrelloni.size()-1 == sceltaRiga) this.listaOmbrelloni.add(appoggio);
            else{
                this.listaOmbrelloni.add(sceltaRiga+1, appoggio);
            }
        }
        this.aggiornaCoordinateOmbrelloniSpiaggia();
    }

    private void aggiornaCoordinateOmbrelloniSpiaggia(){
        int x=0;
        int y=0;
        for(ArrayList<Ombrellone> riga:this.listaOmbrelloni){
            x=0;
            for(Ombrellone ombrellone : riga){
                if(ombrellone == null) continue;
                ombrellone.setLocation(new Coordinate(x,y));
                x++;
            }
            y++;
        }
    }

    private void modificaCoordinate(int sceltaRiga){
        if(this.listaOmbrelloni.size() < sceltaRiga){
            for(int i = sceltaRiga;i<this.listaOmbrelloni.size();i++){
                for(Ombrellone ombrellone : this.listaOmbrelloni.get(i)){
                    if(ombrellone == null) continue;
                    ombrellone.setLocation(new Coordinate(ombrellone.getLocation().getXAxis(),ombrellone.getLocation().getYAxis()+1));
                }
            }
        }
    }

    public void eliminaRiga(int sceltaRiga) {
        this.listaOmbrelloni.remove(sceltaRiga);
        this.aggiornaCoordinateOmbrelloniSpiaggia();
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        str.append("\n");
        for (int i = 0; i < this.listaOmbrelloni.size(); i++) {
            str.append(i).append(" |").append("\t");
            for (int j = 0; j < this.listaOmbrelloni.get(i).size(); j++) {
                if(this.listaOmbrelloni.get(i).get(j)==null) str.append(this.listaOmbrelloni.get(i).get(j)).append("\t");
                else str.append(" ⛱  ").append("\t");
            }
            str.append("\n");
        }
        return str.toString();
    }

}