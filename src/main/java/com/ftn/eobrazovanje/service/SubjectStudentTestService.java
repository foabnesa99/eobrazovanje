package com.ftn.eobrazovanje.service;

import com.ftn.eobrazovanje.domain.entity.relational.SubjectStudentTest;

public interface SubjectStudentTestService {

    SubjectStudentTest create(SubjectStudentTest subjectStudentTest);

    SubjectStudentTest findById(Long id);

    SubjectStudentTest update(SubjectStudentTest subjectStudentTest);

    void delete(Long id);

}
