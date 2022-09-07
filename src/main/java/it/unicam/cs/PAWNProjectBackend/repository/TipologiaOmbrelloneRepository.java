package it.unicam.cs.PAWNProjectBackend.repository;

import it.unicam.cs.PAWNProjectBackend.model.TipologiaOmbrellone;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TipologiaOmbrelloneRepository extends Neo4jRepository<TipologiaOmbrellone,Long> {
}
