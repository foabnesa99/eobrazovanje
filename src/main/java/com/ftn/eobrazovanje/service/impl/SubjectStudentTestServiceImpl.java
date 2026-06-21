package com.ftn.eobrazovanje.service.impl;

import com.ftn.eobrazovanje.dao.SubjectStudentTestRepository;
import com.ftn.eobrazovanje.domain.dto.tests.GradeTestRequest;
import com.ftn.eobrazovanje.domain.dto.tests.StudentTestGradeDto;
import com.ftn.eobrazovanje.domain.entity.relational.SubjectStudentAttendance;
import com.ftn.eobrazovanje.domain.entity.relational.SubjectStudentTest;
import com.ftn.eobrazovanje.domain.entity.relational.SubjectTest;
import com.ftn.eobrazovanje.domain.entity.user.Student;
import com.ftn.eobrazovanje.exception.SubjectStudentTestNotFoundException;
import com.ftn.eobrazovanje.service.StudentService;
import com.ftn.eobrazovanje.service.SubjectStudentAttendanceService;
import com.ftn.eobrazovanje.service.SubjectStudentTestService;
import com.ftn.eobrazovanje.service.SubjectTestService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@Slf4j
@AllArgsConstructor
public class SubjectStudentTestServiceImpl implements SubjectStudentTestService {

    private final SubjectStudentTestRepository subjectStudentTestRepository;
    private final StudentService studentService;
    private final SubjectTestService subjectTestService;
    private final SubjectStudentAttendanceService subjectStudentAttendanceService;

    @Override
    public void gradeTest(GradeTestRequest gradeRequest) {
        SubjectStudentTest test = subjectStudentTestRepository.findById(gradeRequest.getSubjectStudentTestId())
                .orElseThrow(SubjectStudentTestNotFoundException::new);
        test.setGrade(gradeRequest.getGrade());
        test.setPoints(gradeRequest.getPoints());
        test.setPassed(gradeRequest.isPassed());
        subjectStudentTestRepository.save(test);
    }

    @Override
    public void assignCurrentStudentToTest(Long testId) {
        Student student = studentService.getCurrentStudent();
        SubjectTest subjectTest = subjectTestService.findById(testId);
        SubjectStudentAttendance subjectStudentAttendance = subjectStudentAttendanceService.findByStudentIdAndSubjectId(student.getId(), subjectTest.getSubject().getId());
        SubjectStudentTest subjectStudentTest = new SubjectStudentTest();
        subjectStudentTest.setSubjectTest(subjectTest);
        subjectStudentTest.setSubjectStudentAttendance(subjectStudentAttendance);
        subjectStudentTest.setAttended(true);
        subjectStudentTestRepository.save(subjectStudentTest);
    }

    @Override
    public List<StudentTestGradeDto> findStudentTestGradeDtos(Long subjectTestId) {
        return subjectStudentTestRepository.findAllBySubjectTest_Id(subjectTestId).stream()
                .map(test -> new StudentTestGradeDto(
                        test.getId(),
                        test.getSubjectStudentAttendance().getStudent().getUser().getFullName(),
                        test.getGrade(),
                        test.getPoints(),
                        test.isPassed(),
                        test.isAttended()))
                .toList();
    }
}
