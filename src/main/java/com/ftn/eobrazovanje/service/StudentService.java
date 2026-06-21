package com.ftn.eobrazovanje.service;

import com.ftn.eobrazovanje.domain.dto.student.StudentDto;
import com.ftn.eobrazovanje.domain.entity.user.Student;

import java.util.List;

public interface StudentService {

    Student findById(Long id);

    Student findByEmail(String email);

    Student getCurrentStudent();

    Student create(Student student);

    Student update(Student student);

    void delete(Long id);

    List<StudentDto> findAllDtos();

}
