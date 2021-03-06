package it.unicam.cs.PAWNProjectBackend.service;

import it.unicam.cs.PAWNProjectBackend.model.ListaAttrezzatura;

import java.util.*;

public class HandlerAttrezzatura {

    private ListaAttrezzatura listaAttrezzaturaAssociata;
    private DBMSController associatedDBMS;
    Scanner sc;
    private static HandlerAttrezzatura instance = null;

    private HandlerAttrezzatura(){
        this.listaAttrezzaturaAssociata = new ListaAttrezzatura();
        this.associatedDBMS = DBMSController.getInstance();
        this.sc = new Scanner(System.in);
    }

    public static HandlerAttrezzatura getInstance() {
        if (instance == null) {
            instance = new HandlerAttrezzatura();
        }
        return instance;
    }

    public HashMap<Attrezzatura,Integer> ottieniMappaAttrezzature() {
        this.listaAttrezzaturaAssociata.aggiornaMappaAttrezzatura(this.associatedDBMS.ottieniMappaAttrezzature());
        return this.listaAttrezzaturaAssociata.getMappaAttrezzatura();
    }

    public ListaAttrezzatura getListaAttrezzatura(){
        return this.listaAttrezzaturaAssociata;
    }

    public HashMap<Attrezzatura, Integer> getMappaAttrezzaturaAssociata() {
        return this.listaAttrezzaturaAssociata.getMappaAttrezzatura();
    }

    public boolean controlloDisponibilitaAttrezzatura(String nomeAttrezzatura, int numeroAttrezzature) {
        return this.listaAttrezzaturaAssociata.controlloDisponibilitaAttrezzatura(nomeAttrezzatura, numeroAttrezzature);
    }

    public boolean riservaAttrezzature(String nomeAttrezzatura, int numeroAttrezzatureDesiderato) {
        return this.listaAttrezzaturaAssociata.riservaAttrezzatura(nomeAttrezzatura, numeroAttrezzatureDesiderato);
    }


    public void aggiungiAttrezzatura() {
        listaAttrezzaturaAssociata.aggiornaMappaAttrezzatura(associatedDBMS.ottieniMappaAttrezzature());
        listaAttrezzaturaAssociata.printMappaAttrezzature();

        boolean working = true;

        while (working) {

            System.out.println("Digitare 1 per creare una nuova attrezzatura o 2 per aggiungere elementi ad un'attrezzatura esistente");
            int op = this.provaScannerInt();
            if(op == 1){
                creaAttrezzatura();
            }
            if(op == 2){
                aggiungiElementiAdAttrezzatura();
            }
            listaAttrezzaturaAssociata.printMappaAttrezzature();
            System.out.println("Vuoi eseguire altre operazioni sulle attrezzature? [y/n]");
            working = Objects.equals(this.sc.nextLine().trim().toLowerCase(Locale.ROOT), "y");
        }
        if (this.confermaOperazione()) {
            this.associatedDBMS.aggiornaMappaAttrezzature(this.listaAttrezzaturaAssociata.getMappaAttrezzatura());
            System.out.println("Operazioni eseguite");
        } else System.out.println("Operazioni annullate");
    }

    private void aggiungiElementiAdAttrezzatura() {
        String nomeAttrezzatura = this.richiestaNomeAttrezzatura();
        if (this.listaAttrezzaturaAssociata.controlloAttrezzaturaEsistente(nomeAttrezzatura)) {
            this.listaAttrezzaturaAssociata.aggiungiQuantitaAttrezzatura(nomeAttrezzatura, richiestaNumeroAttrezzatura());
        }
    }

    private void creaAttrezzatura(){
        String nomeAttrezzatura;
        String descrizioneAttrezzatura;

        nomeAttrezzatura = richiestaNomeAttrezzatura();

        if (!this.listaAttrezzaturaAssociata.controlloAttrezzaturaEsistente(nomeAttrezzatura)) {
            descrizioneAttrezzatura = richiestaDescrizioneAttrezzatura();

            Attrezzatura nuovaAttrezzatura = new Attrezzatura(nomeAttrezzatura, descrizioneAttrezzatura);
            listaAttrezzaturaAssociata.addAttrezzatura(nuovaAttrezzatura, richiestaNumeroAttrezzatura());
        }
        else System.out.println("L'attrezzatura che si desidera inserire esiste gi??");

    }

    private String richiestaNomeAttrezzatura() {
        boolean flag = true;
        String nuovoNome;
        do {
            System.out.println("Inserire nome dell'attrezzatura");
            nuovoNome = sc.nextLine();
            if (!nuovoNome.isEmpty()){
                flag = false;
            }
        } while (flag);

        return nuovoNome;
    }

    private String richiestaDescrizioneAttrezzatura() {
        String nuovaDescrizione;
        System.out.println("Inserire descrizione della nuova attrezzatura");
        nuovaDescrizione = sc.nextLine();
        return nuovaDescrizione;
    }

    private int richiestaNumeroAttrezzatura() {
        boolean flag = true;
        int nuovaQuantita;
        do {
            System.out.println("Inserire quantit?? di attrezzatura");
            nuovaQuantita = sc.nextInt();
            sc.nextLine();
            if (isPrezzoNegativo(nuovaQuantita)) {
                continue;
            } else {
                flag = false;
            }

        } while (flag);

        return nuovaQuantita;
    }

    private boolean isPrezzoNegativo(int quantita) {
        if (Integer.compare(quantita, 0) < 0) {
            System.out.println("Il prezzo inserito ?? negativo");
            return true;
        }
        return false;
    }

    private boolean confermaOperazione() {
        System.out.println("Confermi l'operazione? [y/n] ");
        return Objects.equals(this.sc.nextLine().trim().toLowerCase(Locale.ROOT), "y");
    }

    public void rimuoviAttrezzatura() {
        listaAttrezzaturaAssociata.aggiornaMappaAttrezzatura(associatedDBMS.ottieniMappaAttrezzature());
        listaAttrezzaturaAssociata.printMappaAttrezzature();

        boolean working = true;

        int quantitaAttrezzatura = 0;
        while (working) {
            String nomeAttrezzatura;

            nomeAttrezzatura = richiestaNomeAttrezzatura();

            quantitaAttrezzatura = richiestaNumeroAttrezzatura();

            if (listaAttrezzaturaAssociata.controlloDisponibilitaAttrezzatura(nomeAttrezzatura, quantitaAttrezzatura)) {
                listaAttrezzaturaAssociata.rimuoviQuantitaAttrezzatura(nomeAttrezzatura, quantitaAttrezzatura);
            } else System.out.println("Non ?? possibile rimuovere " + quantitaAttrezzatura + " elementi, inserire un altro numero.");

            listaAttrezzaturaAssociata.printMappaAttrezzature();
            System.out.println("Rimuovere altre attrezzature? [y/n]");
            working = Objects.equals(this.sc.nextLine().trim().toLowerCase(Locale.ROOT), "y");
        }
        if (this.confermaOperazione()) {
            this.associatedDBMS.aggiornaMappaAttrezzature(this.listaAttrezzaturaAssociata.getMappaAttrezzatura());
            System.out.println("Operazioni eseguite");

        } else System.out.println("Operazioni annullate");
    }


    public void aggiornaListaAttrezzatura(){
        this.listaAttrezzaturaAssociata.aggiornaMappaAttrezzatura(this.associatedDBMS.ottieniMappaAttrezzature());

    }

    private int provaScannerInt(){
        while(true){
            try{
                int intero = this.sc.nextInt();
                this.sc.nextLine();
                return intero;
            } catch (Exception e) {
                this.sc.nextLine();
                System.out.println("Cio' che hai inserito non e' un valore numerico, ritenta ");
            }
        }
    }
}