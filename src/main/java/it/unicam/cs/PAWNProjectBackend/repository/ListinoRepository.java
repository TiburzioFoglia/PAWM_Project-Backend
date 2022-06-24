package it.unicam.cs.PAWNProjectBackend.repository;

import it.unicam.cs.PAWNProjectBackend.model.Listino;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ListinoRepository extends Neo4jRepository<Listino,Long> {
}
