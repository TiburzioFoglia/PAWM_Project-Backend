package it.unicam.cs.PAWNProjectBackend.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;

@Data
@NoArgsConstructor
@Node
public class Coordinate {

    @Id
    @GeneratedValue
    private Long id;
    int xAxis;
    int yAxis;

    public Coordinate(int x, int y){
        this.xAxis = x;
        this.yAxis = y;
    }
}