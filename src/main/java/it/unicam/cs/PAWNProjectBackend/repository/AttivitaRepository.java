package it.unicam.cs.PAWNProjectBackend.repository;

import it.unicam.cs.PAWNProjectBackend.model.Coordinate;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AttivitaRepository extends Neo4jRepository<Coordinate,Long> {
}
