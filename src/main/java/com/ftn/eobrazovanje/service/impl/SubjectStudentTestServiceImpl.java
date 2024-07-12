package com.ftn.eobrazovanje.service.impl;

import com.ftn.eobrazovanje.dao.SubjectStudentTestRepository;
import com.ftn.eobrazovanje.domain.entity.relational.SubjectStudentTest;
import com.ftn.eobrazovanje.exception.SubjectStudentTestNotFoundException;
import com.ftn.eobrazovanje.service.SubjectStudentTestService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@Slf4j
@AllArgsConstructor
public class SubjectStudentTestServiceImpl implements SubjectStudentTestService {

    private final SubjectStudentTestRepository subjectStudentTestRepository;

    @Override
    public SubjectStudentTest create(SubjectStudentTest subjectStudentTest) {
        return subjectStudentTestRepository.save(subjectStudentTest);
    }

    @Override
    public SubjectStudentTest findById(Long id) {
        return subjectStudentTestRepository.findById(id).orElseThrow(SubjectStudentTestNotFoundException::new);
    }

    @Override
    public SubjectStudentTest update(SubjectStudentTest subjectStudentTest) {
        return subjectStudentTestRepository.save(subjectStudentTest);
    }

    @Override
    public void delete(Long id) {
        subjectStudentTestRepository.deleteById(id);
    }
}
