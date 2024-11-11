package edu.depaul.cdm.se452.concept.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import edu.depaul.cdm.se452.concept.rdbm.school.complex.School;
import edu.depaul.cdm.se452.concept.rdbm.school.complex.SchoolRepository;
import edu.depaul.cdm.se452.concept.rdbm.school.complex.University;
import edu.depaul.cdm.se452.concept.rdbm.school.complex.UniversityRepository;

@SpringBootTest
public class UniversityServiceTest {
	@Autowired
	private SchoolRepository schoolRepo;

	@Autowired
	private UniversityRepository uniRepo;

	@Autowired
	private UniversityService uniService;

	@Test
	public void testUniversityService() {
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
		uniService.addUniversity(depaul);
//		uniRepo.save(depaul);

		School cdmFinder = schoolRepo.findByName("CDM");
		cdmFinder.setUniversity(depaul);
		schoolRepo.save(cdmFinder);

		University loyola = new University();
		loyola.setName("Loyla");
		uniService.addUniversity(loyola);
//		uniRepo.save(loyola);

		// This will error because transaction demaraction was not set for the lazy
		// initiation
		University uniFinder2 = uniRepo.findByName("DePaul");
		assertEquals(uniFinder2.getName(), "DePaul");

		// This will not error because the service method defined the transaction
		// demarcation
		University uniFinder = uniService.findUniveristy("DePaul");
		assertNotNull(uniFinder);
	};
}
