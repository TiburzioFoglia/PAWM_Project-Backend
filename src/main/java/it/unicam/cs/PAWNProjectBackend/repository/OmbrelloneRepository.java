package it.unicam.cs.PAWNProjectBackend.repository;

import it.unicam.cs.PAWNProjectBackend.model.Ombrellone;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OmbrelloneRepository extends Neo4jRepository<Ombrellone,Long>{
}
