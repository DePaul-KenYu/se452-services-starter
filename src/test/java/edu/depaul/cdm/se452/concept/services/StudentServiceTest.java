package edu.depaul.cdm.se452.concept.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.hamcrest.CoreMatchers;
import org.hamcrest.core.Is;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.databind.ObjectMapper;

import edu.depaul.cdm.se452.concept.rdbm.school.simple.Student;
import edu.depaul.cdm.se452.concept.rdbm.school.simple.StudentRepository;

@SpringBootTest
@AutoConfigureMockMvc
public class StudentServiceTest {
    private static final String STUDENT_URL = "/api/students";

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private MockMvc mockMvc;


    @Test
    public void getAllStudents() throws Exception {
		// when - action
		ResultActions response = mockMvc.perform(MockMvcRequestBuilders.get(STUDENT_URL));


		var recordCount = (int) studentRepository.count();
		
		// then - verify the output
		response.andExpect(MockMvcResultMatchers.status().isOk());
		response.andExpect(MockMvcResultMatchers.jsonPath("$.size()", CoreMatchers.is(recordCount)));
    }

	@Autowired
	private ObjectMapper objectMapper;

    @Test
    public void addStudent() throws Exception {
		// given - setup or precondition
		Student student = Student.builder().name("peter parker").email("peterparker@nyu.edu").age(20).build();
		String studentAsJson = objectMapper.writeValueAsString(student);

		long beforeSize = studentRepository.count();

		// when - action
		var request = MockMvcRequestBuilders.post(STUDENT_URL);
		request.contentType(MediaType.APPLICATION_JSON);
		request.content(studentAsJson);
		ResultActions response = mockMvc.perform(request);

		var jsonResponse = response.andReturn().getResponse().getContentAsString();
		// then - verify the output
		Student updatedStudent = new ObjectMapper().readValue(jsonResponse, Student.class);

		response.andExpect(MockMvcResultMatchers.status().isOk());
		assertNotEquals(updatedStudent.getId(), student.getId());
    }

    @Test
    public void addStudentValidationFail() throws Exception {
		// given - setup or precondition
		Student student = Student.builder().name("peter parker").build();
		String studentAsJson = objectMapper.writeValueAsString(student);

		long beforeSize = studentRepository.count();

		// when - action
		var request = MockMvcRequestBuilders.post(STUDENT_URL+"/valid");
		request.contentType(MediaType.APPLICATION_JSON);
		request.content(studentAsJson);
		ResultActions response = mockMvc.perform(request);

		var jsonResponse = response.andReturn().getResponse().getContentAsString();

		response.andExpect(MockMvcResultMatchers.status().isBadRequest());
//		response.andExpect(MockMvcResultMatchers.jsonPath("$.email", Is.is("must not be blank")));
    }

    @Test
    public void addStudentValidationPass() throws Exception {
		// given - setup or precondition
		Student student = Student.builder().name("peter parker").email("peter@nyu.edu").age(20).build();
		String studentAsJson = objectMapper.writeValueAsString(student);

		long beforeSize = studentRepository.count();

		// when - action
		var request = MockMvcRequestBuilders.post(STUDENT_URL+"/valid");
		request.contentType(MediaType.APPLICATION_JSON);
		request.content(studentAsJson);
		ResultActions response = mockMvc.perform(request);

		var jsonResponse = response.andReturn().getResponse().getContentAsString();

		response.andExpect(MockMvcResultMatchers.status().isOk());
    }



	@Test
    public void removeStudent() throws Exception {
		// given - setup or precondition
		long beforeSize = studentRepository.count();

		// when - action
		var request = MockMvcRequestBuilders.delete(STUDENT_URL+"/1");
		ResultActions response = mockMvc.perform(request);

		long afterSize = studentRepository.count();

		response.andExpect(MockMvcResultMatchers.status().isOk());
		assertEquals(beforeSize - 1, afterSize);
    }
    
}
