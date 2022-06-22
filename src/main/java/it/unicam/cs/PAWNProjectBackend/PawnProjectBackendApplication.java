package it.unicam.cs.PAWNProjectBackend;

import it.unicam.cs.PAWNProjectBackend.model.Coordinate;
import it.unicam.cs.PAWNProjectBackend.model.Ombrellone;
import it.unicam.cs.PAWNProjectBackend.repository.CoordinateRepository;
import it.unicam.cs.PAWNProjectBackend.repository.OmbrelloneRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Arrays;

@SpringBootApplication
public class PawnProjectBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(PawnProjectBackendApplication.class, args);
	}


	@Bean
	CommandLineRunner initDatabase(CoordinateRepository coordinateRepository, OmbrelloneRepository ombrelloneRepository){
		return args -> {
			coordinateRepository.deleteAll();
			ombrelloneRepository.deleteAll();

			Coordinate coordinateProva = new Coordinate(1,1);
			coordinateRepository.save(coordinateProva);

			Ombrellone ombrelloneProva = new Ombrellone();
			ombrelloneProva.setLocation(coordinateProva);
			ombrelloneRepository.save(ombrelloneProva);




		};
	}
}
