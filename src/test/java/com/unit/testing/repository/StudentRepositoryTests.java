package com.unit.testing.repository;

import com.unit.testing.model.Student;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@DataJpaTest
public class StudentRepositoryTests {

    @Autowired
    private StudentRepository studentRepository;

    private Student student;

    @BeforeEach
    public void setup(){
        student = Student.builder()
                .firstName("Pedro")
                .lastName("Freitas")
                .email("pedrofreitas@tests.com.br")
                .build();
    }

    @DisplayName("JUnit test for save student operation")
    @Test
    public void givenStudentObject_whenSave_thenReturnSavedStudent(){
        //given

        //when
        Student savedStudent = studentRepository.save(student);

        //then
        assertThat(savedStudent).isNotNull();
    }


    @DisplayName("JUnit test for get all students operation")
    @Test
    public void givenStudentList_whenFindAll_thenStudentList(){
        Student s2 = Student.builder()
                .firstName("Ana")
                .lastName("Beatriz")
                .email("ana@tests.com.br")
                .build();

        studentRepository.saveAll(Arrays.asList(student, s2));

        //when
        List<Student> list = studentRepository.findAll();

        //then
        assertThat(list).isNotNull();
        assertThat(list).size().isEqualTo(2);
    }

    @DisplayName("JUnit test for update student operation")
    @Test
    public void givenStudentObject_whenUpdateStudent_thenReturnUpdatedStudent(){
        //given
        studentRepository.save(student);

        //when
        Student student1 = studentRepository.findById(student.getId()).get();
        student1.setEmail("pfreitas@tests.com.br");
        Student newStudentData = studentRepository.save(student1);

        //then
        assertThat(newStudentData.getEmail()).isEqualTo("pfreitas@tests.com.br");
    }

    @DisplayName("JUnit test for delete student operation")
    @Test
    public void givenStudentObject_whenDelete_thenRemoveStudent(){
        //given
        studentRepository.save(student);

        //when
        studentRepository.deleteById(student.getId());
        Optional<Student> studentOptional = studentRepository.findById(student.getId());

        //then
        assertThat(studentOptional).isEmpty();
    }
}
