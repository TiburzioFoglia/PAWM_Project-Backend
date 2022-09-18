package it.unicam.cs.PAWNProjectBackend.repository;

import it.unicam.cs.PAWNProjectBackend.model.FasciaDiPrezzo;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FasciaDiPrezzoRepository extends Neo4jRepository<FasciaDiPrezzo,Long> {
}
