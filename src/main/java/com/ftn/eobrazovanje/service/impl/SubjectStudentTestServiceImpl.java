package com.ftn.eobrazovanje.service.impl;

import com.ftn.eobrazovanje.dao.SubjectStudentTestRepository;
import com.ftn.eobrazovanje.domain.entity.relational.SubjectStudentAttendance;
import com.ftn.eobrazovanje.domain.entity.relational.SubjectStudentTest;
import com.ftn.eobrazovanje.domain.entity.relational.SubjectTest;
import com.ftn.eobrazovanje.domain.entity.user.Student;
import com.ftn.eobrazovanje.domain.entity.user.User;
import com.ftn.eobrazovanje.exception.SubjectStudentTestNotFoundException;
import com.ftn.eobrazovanje.service.*;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
@Slf4j
@AllArgsConstructor
public class SubjectStudentTestServiceImpl implements SubjectStudentTestService {

    private final SubjectStudentTestRepository subjectStudentTestRepository;
    private final StudentService studentService;
    private final SubjectService subjectService;
    private final SubjectTestService subjectTestService;
    private final SubjectStudentAttendanceService subjectStudentAttendanceService;

    @Override
    public SubjectStudentTest create(SubjectStudentTest subjectStudentTest) {
        return subjectStudentTestRepository.save(subjectStudentTest);
    }

    @Override
    public SubjectStudentTest findById(Long id) {
        return subjectStudentTestRepository.findById(id).orElseThrow(SubjectStudentTestNotFoundException::new);
    }

    @Override
    public Optional<SubjectStudentTest> findByStudentSubjectAttendanceIdAndSubjectTestId(Long studentSubjectAttendanceId, Long testId){
        return subjectStudentTestRepository.findBySubjectStudentAttendance_IdAndSubjectTest_Id(studentSubjectAttendanceId, testId);
    }

    @Override
    public SubjectStudentTest update(SubjectStudentTest subjectStudentTest) {
        return subjectStudentTestRepository.save(subjectStudentTest);
    }

    @Override
    public void delete(Long id) {
        subjectStudentTestRepository.deleteById(id);
    }

    @Override
    public void assignStudentToTest(Long testId, Student student) {
        SubjectTest subjectTest = subjectTestService.findById(testId);
        SubjectStudentAttendance subjectStudentAttendance = subjectStudentAttendanceService.findByStudentIdAndSubjectId(student.getId(), subjectTest.getSubject().getId());
        SubjectStudentTest subjectStudentTest = new SubjectStudentTest();
        subjectStudentTest.setSubjectTest(subjectTest);
        subjectStudentTest.setSubjectStudentAttendance(subjectStudentAttendance);
        subjectStudentTest.setAttended(true);
        create(subjectStudentTest);
    }
}
