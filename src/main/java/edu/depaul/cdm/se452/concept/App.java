package edu.depaul.cdm.se452.concept;


import java.util.ArrayList;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import edu.depaul.cdm.se452.concept.rdbm.school.complex.School;
import edu.depaul.cdm.se452.concept.rdbm.school.complex.SchoolRepository;
import edu.depaul.cdm.se452.concept.rdbm.school.complex.University;
import edu.depaul.cdm.se452.concept.rdbm.school.complex.UniversityRepository;
import edu.depaul.cdm.se452.concept.services.UniversityService;
import lombok.extern.log4j.Log4j2;

@SpringBootApplication
@Log4j2
public class App {

	public static void main(String[] args) {
		SpringApplication.run(App.class, args);
	}
	@Bean
	CommandLineRunner addOneToManyDemoRun(SchoolRepository schoolRepo, UniversityRepository uniRepo, UniversityService uniService) {
		return args -> {
			ArrayList<School> schools = new ArrayList<School>();

			School cdm = new School();
			cdm.setName("CDM");
			schoolRepo.save(cdm);
			schools.add(cdm);

			School kellSchool = new School();
			kellSchool.setName("KellStadt");
			schoolRepo.save(kellSchool);
			schools.add(kellSchool);

			School under = new School();
			under.setName("Under");
			schoolRepo.save(under);
			schools.add(under);

			University depaul = new University();
			depaul.setName("DePaul");
			depaul.setSchools(schools);
			uniRepo.save(depaul);

			School cdmFinder = schoolRepo.findByName("CDM");
			cdmFinder.setUniversity(depaul);
			schoolRepo.save(cdmFinder);

			University loyola = new University();
			loyola.setName("Loyla");
			uniRepo.save(loyola);

			// This will error because transaction demaraction was not set for the lazy initiation
			University uniFinder2 = uniRepo.findByName("DePaul");
			log.info(uniFinder2);

			// This will not error because the service method defined the transaction demarcation
			University uniFinder = uniService.findUniveristy("DePaul");
			log.info(uniFinder);


		};
	}

}
