package com.unit.testing.service;

import com.unit.testing.model.Student;

import java.util.List;

public interface StudentService {

    Student saveStudent(Student student);
    List<Student> getAllStudents();
    Student updateStudent(Student student);
    void deleteStudent(Long id);

}
