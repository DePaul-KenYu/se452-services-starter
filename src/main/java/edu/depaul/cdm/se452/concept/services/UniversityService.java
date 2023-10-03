package edu.depaul.cdm.se452.concept.services;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.depaul.cdm.se452.concept.rdbm.school.complex.University;
import edu.depaul.cdm.se452.concept.rdbm.school.complex.UniversityRepository;
import jakarta.transaction.Transactional;
import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
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

}
