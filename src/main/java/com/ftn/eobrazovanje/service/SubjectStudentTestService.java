package com.ftn.eobrazovanje.service;

import com.ftn.eobrazovanje.domain.dto.tests.GradeTestRequest;
import com.ftn.eobrazovanje.domain.dto.tests.StudentTestGradeDto;

import java.util.List;

public interface SubjectStudentTestService {

    void gradeTest(GradeTestRequest gradeRequest);

    void assignCurrentStudentToTest(Long testId);

    List<StudentTestGradeDto> findStudentTestGradeDtos(Long subjectTestId);
}
