package edu.depaul.cdm.se452.concept.services;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import edu.depaul.cdm.se452.concept.rdbm.school.simple.Student;
import edu.depaul.cdm.se452.concept.rdbm.school.simple.StudentRepository;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.extern.log4j.Log4j2;

@RestController
@RequestMapping("/api/students")
@Log4j2
public class StudentService {
    @Autowired
    private StudentRepository repo;

    @GetMapping
    public List<Student> list() {
        log.traceEntry("Enter list");
        List<Student> retval = StreamSupport.stream(repo.findAll().spliterator(), false).collect(Collectors.toList());
        log.traceExit("Exit list", retval);
        return retval;
    }

    @PostMapping
    public Student save(@RequestBody Student student) {
        log.traceEntry("enter save", student);
        repo.save(student);
        log.traceExit("exit save", student);
        return student;
    }


    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id ) {
        log.traceEntry("Enter delete", id);
        repo.deleteById(id);
        log.traceExit("Exit delete");
    }           

    @PostMapping("/valid")
    @Operation(summary = "Save the student and returns the student id")
    public ResponseEntity<String> validatedSave(@Valid @RequestBody Student student) {
        log.traceEntry("enter save", student);
        repo.save(student);
        log.traceExit("exit save", student);
        return ResponseEntity.ok("new id is " + student.getId());
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
