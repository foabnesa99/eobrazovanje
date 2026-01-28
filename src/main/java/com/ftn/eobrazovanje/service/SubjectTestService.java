package com.ftn.eobrazovanje.service;

import com.ftn.eobrazovanje.domain.dto.tests.StudentTestSimpleDto;
import com.ftn.eobrazovanje.domain.entity.relational.SubjectTest;
import com.ftn.eobrazovanje.domain.entity.user.Student;

import java.util.List;

public interface SubjectTestService {

    SubjectTest create(SubjectTest subjectTest);
    SubjectTest findById(Long id);
    SubjectTest update(SubjectTest subjectTest);
    void deleteById(Long id);

    List<StudentTestSimpleDto> getAvailableTestsForStudent(Student student);
}
