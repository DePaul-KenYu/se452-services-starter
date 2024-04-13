package edu.depaul.cdm.se452.concept.services;

import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import edu.depaul.cdm.se452.concept.rdbm.school.simple.Course;

/**
 * Test out the service
 */
@SpringBootTest
public class CourseServiceTest {
    @Autowired
    private CourseService service;

    @Test
    public void testAddCourse() {
        Course course = new Course();
        course.setDept("SE");
        course.setNum("554");
        long b4 = service.list().size();
        service.save(course);
        long after = service.list().size();
        // since adding, # of courses should be the 1 more than before
        assertEquals(b4 + 1, after);
    }   
    
    @Test
    public void testUpdateCourse() {
        // In order to update, need to find something so will be updating the first one
        List<Course> courses = service.list();
        Course course1 = courses.get(0);
        course1.setNum(course1.getNum() + "-U");        
        
        service.save(course1);
        long after = service.list().size();
        // since updating, # of courses should be the same
        assertEquals(courses.size(), after);
    }

    @Test
    public void testDelete() {
        // In order to delete, need to find something to delete so will delete the first one
        List<Course> courses = service.list();
        service.delete(courses.get(0).getId());
        long after = service.list().size();
        // since removing 1, the list should be 1 less than original list
        assertEquals(courses.size() - 1, after);
    }


}
