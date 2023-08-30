package edu.depaul.cdm.se452.concept.services;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import edu.depaul.cdm.se452.concept.rdbm.school.simple.Course;
import edu.depaul.cdm.se452.concept.rdbm.school.simple.CourseRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.log4j.Log4j2;

/**
 * Documented services
 */
@RestController
@RequestMapping("/api/course")
@Tag(name = "Course", description = "Everything about your Course")
@Log4j2
public class CourseService {
    @Autowired
    private CourseRepository repo;

    @GetMapping
    @Operation(summary = "Returns all the course in the database")
    @ApiResponse(responseCode = "200", description = "valid response", 
        content = {@Content(mediaType="application/json", schema=@Schema(implementation=Course.class))})
    public List<Course> list() {
        log.traceEntry("Enter list");
        List<Course> retval = StreamSupport.stream(repo.findAll().spliterator(), false).collect(Collectors.toList());
        log.traceExit("Exit list", retval);        
        return retval;
    }

    @PostMapping
    @Operation(summary = "Save the course and returns the course id")
    public long save(Course course) {
        log.traceEntry("enter save", course);
        repo.save(course);
        log.traceExit("exit save", course);        
        return course.getId();
    }

    @PostMapping("/validated")
    @Operation(summary = "Save the course and returns the course id")
    public ResponseEntity<String> validatedSave(@Valid @RequestBody Course course) {
        log.traceEntry("enter save", course);
        repo.save(course);
        log.traceExit("exit save", course);
        return ResponseEntity.ok("new id is " + course.getId());
    }

    @DeleteMapping
    @Operation(summary = "Delete the course")
    public void delete(long id) {
        log.traceEntry("Enter delete", id);
        repo.deleteById(id);
        log.traceExit("Exit delete");
    }
    
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }
}