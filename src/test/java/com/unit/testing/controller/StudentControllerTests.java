package com.unit.testing.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.unit.testing.model.Student;
import com.unit.testing.service.StudentService;
import static org.hamcrest.CoreMatchers.is;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import static org.mockito.BDDMockito.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.ArrayList;
import java.util.List;

@WebMvcTest
public class StudentControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private StudentService studentService;

    @Test
    public void givenStudentObject_whenCreateStudent_thenReturnSavedStudent() throws Exception {
        // given - precondition or setup
        Student student = Student.builder()
                .firstName("Pedro")
                .lastName("Freitas")
                .email("pedrofreitas@tests.com")
                .build();
        given(studentService.saveStudent(ArgumentMatchers.any(Student.class)))
                .willAnswer((invocation)-> invocation.getArgument(0));

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

    @Test
    public void givenListOfStudent_whenGetAllStudents_thenReturnStudentsList() throws Exception{
        // given - precondition or setup
        List<Student> studentList = new ArrayList<>();
        studentList.add(Student.builder()
                .firstName("Pedro").lastName("Freitas").email("pedrofreitas@test.com").build());
        studentList.add(Student.builder()
                .firstName("Ana").lastName("Beatriz").email("ana@test.com").build());
        given(studentService.getAllStudents()).willReturn(studentList);

        // when -  action or the behaviour that we are going test
        ResultActions response = mockMvc.perform(get("/student"));

        // then - verify the output
        response.andExpect(status().isOk())
                .andExpect(jsonPath("$.size()",
                        is(studentList.size())));
    }

    @Test
    public void givenStudentId_whenDeleteStudent_thenReturnOk() throws Exception{
        // given - precondition or setup
        Long studentId = 1L;
        willDoNothing().given(studentService).deleteStudent(studentId);

        // when -  action or the behaviour that we are going test
        ResultActions response = mockMvc.perform(delete("/student/{id}", studentId));

        // then - verify the output
        response.andExpect(status().isOk()).andDo(print());
    }
}
