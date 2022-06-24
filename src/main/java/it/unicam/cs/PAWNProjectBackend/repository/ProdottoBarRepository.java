package it.unicam.cs.PAWNProjectBackend.repository;

import it.unicam.cs.PAWNProjectBackend.model.ProdottoBar;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProdottoBarRepository extends Neo4jRepository<ProdottoBar,Long> {
}
