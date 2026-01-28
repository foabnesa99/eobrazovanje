package com.ftn.eobrazovanje.service.impl;

import com.ftn.eobrazovanje.dao.SubjectTestRepository;
import com.ftn.eobrazovanje.domain.dto.tests.StudentTestSimpleDto;
import com.ftn.eobrazovanje.domain.entity.relational.SubjectStudentAttendance;
import com.ftn.eobrazovanje.domain.entity.relational.SubjectTest;
import com.ftn.eobrazovanje.domain.entity.user.Student;
import com.ftn.eobrazovanje.exception.SubjectTestNotFoundException;
import com.ftn.eobrazovanje.service.SubjectStudentAttendanceService;
import com.ftn.eobrazovanje.service.SubjectTestService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@Slf4j
@AllArgsConstructor
public class SubjectTestServiceImpl implements SubjectTestService {

    private final SubjectTestRepository subjectTestRepository;
    private final SubjectStudentAttendanceService subjectStudentAttendanceService;

    @Override
    public SubjectTest create(SubjectTest subjectTest) {
        return subjectTestRepository.save(subjectTest);
    }

    @Override
    public SubjectTest findById(Long id) {
        return subjectTestRepository.findById(id).orElseThrow(SubjectTestNotFoundException::new);
    }

    @Override
    public SubjectTest update(SubjectTest subjectTest) {
        return subjectTestRepository.save(subjectTest);
    }

    @Override
    public void deleteById(Long id) {
        subjectTestRepository.deleteById(id);
    }

    @Override
    public List<StudentTestSimpleDto> getAvailableTestsForStudent(Student student) {
        return subjectTestRepository.findAllStudentTestSimpleDtosByStudentId(student.getId(), LocalDateTime.now().plusDays(1));
    }
}
