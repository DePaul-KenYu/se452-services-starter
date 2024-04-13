package edu.depaul.cdm.se452.concept.services;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import edu.depaul.cdm.se452.concept.rdbm.school.simple.Course;
import edu.depaul.cdm.se452.concept.rdbm.school.simple.CourseRepository;
import lombok.extern.log4j.Log4j2;

/**
 * Services
 */
@Service
@Log4j2
public class CourseService {
    // @Autowired ensure that CourseRepository interface is properly constructed 
    // and available before CourseService is ready to use
    @Autowired
    private CourseRepository repo;

    public List<Course> list() {
        log.traceEntry("Enter list");
        List<Course> retval = StreamSupport.stream(repo.findAll().spliterator(), false).collect(Collectors.toList());
        log.traceExit("Exit list", retval);        
        return retval;
    }

    public Course save(Course course) {
        log.traceEntry("enter save", course);
        repo.save(course);
        log.traceExit("exit save", course);        
        return course;
    }

    public void delete(long id) {
        log.traceEntry("Enter delete", id);
        repo.deleteById(id);
        log.traceExit("Exit delete");
    }
    
}