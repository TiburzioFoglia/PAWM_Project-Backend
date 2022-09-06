package it.unicam.cs.PAWNProjectBackend;

import it.unicam.cs.PAWNProjectBackend.model.Coordinate;
import it.unicam.cs.PAWNProjectBackend.model.Ombrellone;
import it.unicam.cs.PAWNProjectBackend.repository.CoordinateRepository;
import it.unicam.cs.PAWNProjectBackend.repository.OmbrelloneRepository;
import it.unicam.cs.PAWNProjectBackend.repository.SpiaggiaRepository;
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
								   SpiaggiaRepository spiaggiaRepository){
		return args -> {
			coordinateRepository.deleteAll();
			ombrelloneRepository.deleteAll();
			spiaggiaRepository.deleteAll();

		};
	}
}
