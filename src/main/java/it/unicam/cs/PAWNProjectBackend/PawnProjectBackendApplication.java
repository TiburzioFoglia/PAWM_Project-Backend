package it.unicam.cs.PAWNProjectBackend;

import it.unicam.cs.PAWNProjectBackend.model.*;
import it.unicam.cs.PAWNProjectBackend.repository.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.Collection;

@SpringBootApplication
@Slf4j
public class PawnProjectBackendApplication {

	@Autowired
	PasswordEncoder passwordEncoder;

	public static void main(String[] args) {
		SpringApplication.run(PawnProjectBackendApplication.class, args);
	}


	@Bean
	CommandLineRunner initDatabase(CoordinateRepository coordinateRepository, OmbrelloneRepository ombrelloneRepository,
								   SpiaggiaRepository spiaggiaRepository,
								   TipologiaOmbrelloneRepository tipologiaOmbrelloneRepository,
								   ListinoRepository listinoRepository, UserRepository userRepository,
								   RoleRepository roleRepository){


		return args -> {
			coordinateRepository.deleteAll();
			ombrelloneRepository.deleteAll();
			spiaggiaRepository.deleteAll();
			tipologiaOmbrelloneRepository.deleteAll();
			listinoRepository.deleteAll();
			roleRepository.deleteAll();
			userRepository.deleteAll();

			TipologiaOmbrellone tipologiaOmbrellone = new TipologiaOmbrellone("Normale","un normale ombrellone");
			tipologiaOmbrelloneRepository.save(tipologiaOmbrellone);
			log.info("Salvata la tipologia ombrellone : {}",tipologiaOmbrellone);
			Listino listino =  new Listino();
			listino.setPrezzoBaseLettino(5.0);
			log.info("Prezzo base lettino : {}",listino.getPrezzoBaseLettino());
			listino.setPrezzoBaseOmbrellone(2.0);
			log.info("Prezzo base ombrellone : {}",listino.getPrezzoBaseOmbrellone());
			ListinoTipologiaOmbrelloneRel rel = new ListinoTipologiaOmbrelloneRel(tipologiaOmbrellone,1.0);
			listino.getPrezziTipologia().add(rel);
			listinoRepository.save(listino);

			Role admin = new Role("Admin","Administrator of the application");
			roleRepository.save(admin);
			log.info("Nuovo ruolo : {}",admin);
			Role user = new Role("User","Generic user of the application");
			roleRepository.save(user);
			log.info("Nuovo ruolo : {}",user);


			Collection<Role> ruoli = new ArrayList<>();
			ruoli.add(admin);
			User adminUser = new User("admin","Mario","Rossi",getEncodedPassword("marioPass"), ruoli);
			userRepository.save(adminUser);
			log.info("Nuovo user : {}",adminUser);
			ruoli = new ArrayList<>();
			ruoli.add(user);
			User genericUser = new User("genericUser","Giorgio","Grigio",getEncodedPassword("giorgioPass"), ruoli);
			userRepository.save(genericUser);
			log.info("Nuovo user : {}",genericUser);

		};

	}

	public String getEncodedPassword(String password){
		return passwordEncoder.encode(password);
	}
}
