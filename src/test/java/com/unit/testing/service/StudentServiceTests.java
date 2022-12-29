package com.unit.testing.service;

import com.unit.testing.model.Student;
import com.unit.testing.repository.StudentRepository;
import com.unit.testing.service.impl.StudentServiceImpl;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

@ExtendWith(MockitoExtension.class)
public class StudentServiceTests {

    @Mock
    private StudentRepository studentRepository;

    @InjectMocks
    private StudentServiceImpl studentService;

    private Student student;

    @BeforeEach
    public void setup(){
        student = Student.builder()
                .firstName("Pedro")
                .lastName("Freitas")
                .email("pedrofreitas@tests.com.br")
                .build();
    }

    @DisplayName("JUnit test for saveStudent method")
    @Test
    public void givenStudentObject_whenSaveStudent_thenReturnStudentObject(){
        //given
        given(studentRepository.save(student)).willReturn(student);

        //when
        Student savedStudent = studentService.saveStudent(student);

        //then
        assertThat(savedStudent).isNotNull();
    }

    @DisplayName("JUnit test for getAllStudents method")
    @Test
    public void givenStudentsList_whenGetAllStudents_thenReturnStudentsList(){
        //given
        Student student2 = Student.builder()
                .firstName("Ana")
                .lastName("Beatriz")
                .email("ana@tests.com.br")
                .build();

        given(studentRepository.findAll()).willReturn(List.of(student, student2));

        //when
        List<Student> list = studentService.getAllStudents();

        //then
        assertThat(list).isNotNull();
        assertThat(list).size().isEqualTo(2);
    }

    @DisplayName("JUnit test for updateStudent method")
    @Test
    public void givenStudentObject_whenUpdateStudent_thenReturnNewStudentObject(){
        //given
        given(studentRepository.save(student)).willReturn(student);
        student.setEmail("pedrof@tests.com.br");

        //when
        Student updatedStudent = studentService.updateStudent(student);

        //then
        assertThat(updatedStudent).isNotNull();
        assertThat(updatedStudent.getEmail()).isEqualTo("pedrof@tests.com.br");
    }

    @DisplayName("JUnit test for deleteStudent method")
    @Test
    public void givenStudentId_whenDeleteStudent_thenReturnNothing(){
        //given
        willDoNothing().given(studentRepository).deleteById(student.getId());

        //when
        studentService.deleteStudent(student.getId());

        //then
        verify(studentRepository, times(1)).deleteById(student.getId());
    }

}
