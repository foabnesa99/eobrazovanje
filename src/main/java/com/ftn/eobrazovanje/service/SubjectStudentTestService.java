package com.ftn.eobrazovanje.service;

import com.ftn.eobrazovanje.domain.entity.relational.SubjectStudentTest;
import com.ftn.eobrazovanje.domain.entity.user.Student;

import java.util.Optional;

public interface SubjectStudentTestService {

    SubjectStudentTest create(SubjectStudentTest subjectStudentTest);

    SubjectStudentTest findById(Long id);

    Optional<SubjectStudentTest> findByStudentSubjectAttendanceIdAndSubjectTestId(Long studentSubjectAttendanceId, Long testId);

    SubjectStudentTest update(SubjectStudentTest subjectStudentTest);

    void delete(Long id);

    void assignStudentToTest(Long testId, Student student);
}
