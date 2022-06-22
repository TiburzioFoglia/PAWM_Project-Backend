package it.unicam.cs.PAWNProjectBackend.model;

import java.util.ArrayList;
import java.util.HashMap;

public class Menu { //TODO eliminare e controllare con le altre classi

    private ArrayList<ProdottoBar> listaProdotti;
    private HandlerListino handlerListinoAssociato;

    public Menu(HandlerListino handlerListinoAssociato){
        this.handlerListinoAssociato = handlerListinoAssociato;
    }

    public HashMap<ProdottoBar,Double> ottieniProdottiEPrezzi(){
        return this.handlerListinoAssociato.getPrezziBar();
    }
}