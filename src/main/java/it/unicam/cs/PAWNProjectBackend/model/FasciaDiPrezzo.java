package it.unicam.cs.PAWNProjectBackend.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;

import java.util.Objects;

@Data
@NoArgsConstructor
@Node
public class FasciaDiPrezzo {

    @Id
    @GeneratedValue
    private Long id;
    private String nome;
    private Coordinate coordinateInizio;
    private Coordinate coordinateFine;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FasciaDiPrezzo that = (FasciaDiPrezzo) o;
        return Objects.equals(id, that.id) && Objects.equals(nome, that.nome) && Objects.equals(coordinateInizio, that.coordinateInizio) && Objects.equals(coordinateFine, that.coordinateFine);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nome, coordinateInizio, coordinateFine, id);
    }


    public FasciaDiPrezzo(String nome){
        this(nome,null,null);
    }

    public FasciaDiPrezzo(String nome, Coordinate coordinateInizio ,Coordinate coordinateFine){
        this.nome = nome;
        this.coordinateInizio = coordinateInizio;
        this.coordinateFine = coordinateFine;
    }
}