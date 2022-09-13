package it.unicam.cs.PAWNProjectBackend;

import it.unicam.cs.PAWNProjectBackend.model.*;
import it.unicam.cs.PAWNProjectBackend.repository.*;
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
								   TipologiaOmbrelloneRepository tipologiaOmbrelloneRepository,
								   ListinoRepository listinoRepository){
		return args -> {
			coordinateRepository.deleteAll();
			ombrelloneRepository.deleteAll();
			spiaggiaRepository.deleteAll();
			tipologiaOmbrelloneRepository.deleteAll();
			listinoRepository.deleteAll();

			TipologiaOmbrellone tipologiaOmbrellone = new TipologiaOmbrellone("Normale","un normale ombrellone");
			log.info("Creata la tipologia ombrellone : {}",tipologiaOmbrellone);
			tipologiaOmbrelloneRepository.save(tipologiaOmbrellone);
			log.info("Salvata la tipologia ombrellone : {}",tipologiaOmbrellone);
			Listino listino =  new Listino();
			listino.setPrezzoBaseLettino(5.0);
			listino.setPrezzoBaseOmbrellone(2.0);
			ListinoTipologiaOmbrelloneRel rel = new ListinoTipologiaOmbrelloneRel(tipologiaOmbrellone,1.0);
			listino.getPrezziTipologia().add(rel);
			listinoRepository.save(listino);

		};
	}
}
