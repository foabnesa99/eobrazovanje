package com.ftn.eobrazovanje.service.impl;

import com.ftn.eobrazovanje.dao.SubjectProfessorRepository;
import com.ftn.eobrazovanje.domain.entity.relational.SubjectProfessor;
import com.ftn.eobrazovanje.exception.SubjectProfessorNotFoundException;
import com.ftn.eobrazovanje.service.SubjectProfessorService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@Slf4j
@AllArgsConstructor
public class SubjectProfessorServiceImpl implements SubjectProfessorService {

    private final SubjectProfessorRepository subjectProfessorRepository;

    @Override
    public SubjectProfessor create(SubjectProfessor subjectProfessor) {
        return subjectProfessorRepository.save(subjectProfessor);
    }

    @Override
    public SubjectProfessor findById(Long subjectId, Long professorId) {
        return subjectProfessorRepository.findById(new SubjectProfessor.SubjectProfessorId(subjectId, professorId)).orElseThrow(SubjectProfessorNotFoundException::new);
    }

    @Override
    public SubjectProfessor update(SubjectProfessor subjectProfessor) {
        return subjectProfessorRepository.save(subjectProfessor);
    }

    @Override
    public void delete(Long subjectId, Long professorId) {
        subjectProfessorRepository.deleteById(new SubjectProfessor.SubjectProfessorId(subjectId, professorId));
    }
}
