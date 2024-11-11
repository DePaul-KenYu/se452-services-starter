package edu.depaul.cdm.se452.concept.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.depaul.cdm.se452.concept.rdbm.school.complex.University;
import edu.depaul.cdm.se452.concept.rdbm.school.complex.UniversityRepository;
import jakarta.persistence.EntityExistsException;
import jakarta.transaction.Transactional;
import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
@Transactional(Transactional.TxType.REQUIRED)
public class UniversityService {
    @Autowired
    private UniversityRepository repo;

    @Transactional
    public University findUniveristy(String name) {
        log.traceEntry("Enter find");
        University retval = repo.findByName(name);
        log.traceExit("Exit find", retval);
        return retval;
    }

    @Transactional(
        rollbackOn = IllegalArgumentException.class,
        dontRollbackOn = EntityExistsException.class)
    public University addUniversity(University university) {
        log.traceEntry("Enter add", university);
        University retval = repo.save(university);
        log.traceExit("Exit add", retval);
        return retval;
    }


}
