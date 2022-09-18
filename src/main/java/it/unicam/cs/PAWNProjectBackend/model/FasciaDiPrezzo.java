package it.unicam.cs.PAWNProjectBackend.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;


@Data
@NoArgsConstructor
@Node
public class FasciaDiPrezzo {

    @Id
    @GeneratedValue
    private Long id;
    private String nome;

    private Collection<Integer> righeComprese = new ArrayList<>();

    public FasciaDiPrezzo(String nome) {
        this.nome = nome;
        this.righeComprese = new ArrayList<>();
    }

    public FasciaDiPrezzo(String nome, Collection<Integer> righe){
        this.nome = nome;
        this.righeComprese = righe;
    }


}
