package edu.depaul.cdm.se452.concept.rdbm.school.simple;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import lombok.Data;

/**
 * Minimialist code to map against database when then table/column is same as the class/property
 * Validation message example using resource bundle
 */
@Data
@Entity
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotBlank(message = "course dept must be set")
    @Size(min= 2,max = 4)
    private String dept;

    @NotBlank
    @Size(min= 2,max = 7)
    private String num;

}