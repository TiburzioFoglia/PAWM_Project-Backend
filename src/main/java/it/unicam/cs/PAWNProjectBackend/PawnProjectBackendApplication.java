package it.unicam.cs.PAWNProjectBackend;

import it.unicam.cs.PAWNProjectBackend.model.Coordinate;
import it.unicam.cs.PAWNProjectBackend.model.Ombrellone;
import it.unicam.cs.PAWNProjectBackend.model.TipologiaOmbrellone;
import it.unicam.cs.PAWNProjectBackend.repository.CoordinateRepository;
import it.unicam.cs.PAWNProjectBackend.repository.OmbrelloneRepository;
import it.unicam.cs.PAWNProjectBackend.repository.SpiaggiaRepository;
import it.unicam.cs.PAWNProjectBackend.repository.TipologiaOmbrelloneRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Arrays;

@SpringBootApplication
@Slf4j
public class PawnProjectBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(PawnProjectBackendApplication.class, args);
	}


	@Bean
	CommandLineRunner initDatabase(CoordinateRepository coordinateRepository, OmbrelloneRepository ombrelloneRepository,
								   SpiaggiaRepository spiaggiaRepository,
								   TipologiaOmbrelloneRepository tipologiaOmbrelloneRepository){
		return args -> {
			coordinateRepository.deleteAll();
			ombrelloneRepository.deleteAll();
			spiaggiaRepository.deleteAll();
			tipologiaOmbrelloneRepository.deleteAll();

			TipologiaOmbrellone tipologiaOmbrellone = new TipologiaOmbrellone("Normale","un normale ombrellone");
			log.info("Creata la tipologia ombrellone : {}",tipologiaOmbrellone);
			tipologiaOmbrelloneRepository.save(tipologiaOmbrellone);
			log.info("Salvata la tipologia ombrellone : {}",tipologiaOmbrellone);

		};
	}
}
