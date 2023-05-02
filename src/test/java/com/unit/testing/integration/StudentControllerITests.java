package com.unit.testing.integration;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.unit.testing.model.Student;
import com.unit.testing.repository.StudentRepository;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.CoreMatchers.is;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class StudentControllerITests {
    
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private ObjectMapper objectMapper;

    // to keep the database clean before each test
    @BeforeEach
    void setup() {
        studentRepository.deleteAll();
    }    

    @Test
    public void givenStudentObject_whenCreateStudent_thenReturnSavedStudent() throws Exception {
        // given - precondition or setup
        Student student = Student.builder()
                .firstName("Pedro")
                .lastName("Freitas")
                .email("pedrofreitas@tests.com")
                .build();
        
                // when - action or behaviour that we are going test
        ResultActions response = mockMvc.perform(post("/student")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(student)));

        //then
        response.andExpect(status().isCreated())
                .andExpect(jsonPath("$.firstName", is(student.getFirstName())))
                .andExpect(jsonPath("$.lastName", is(student.getLastName())))
                .andExpect(jsonPath("$.email", is(student.getEmail())));
    }
}
