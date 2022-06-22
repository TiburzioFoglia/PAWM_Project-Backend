package it.unicam.cs.PAWNProjectBackend.repository;


import it.unicam.cs.PAWNProjectBackend.model.Spiaggia;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SpiaggiaRepository extends Neo4jRepository<Spiaggia,Long>{
}