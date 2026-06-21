package com.ftn.eobrazovanje.service;

import com.ftn.eobrazovanje.domain.dto.subjectProfessor.SubjectProfessorCreateRequest;
import com.ftn.eobrazovanje.domain.dto.subjectProfessor.SubjectProfessorPairingDto;
import com.ftn.eobrazovanje.domain.dto.subjectProfessor.SubjectProfessorSimpleDto;
import com.ftn.eobrazovanje.domain.dto.tests.StudentTestSimpleDto;
import com.ftn.eobrazovanje.domain.entity.relational.SubjectProfessor;

import java.util.List;
import java.util.Optional;

public interface SubjectProfessorService {

    SubjectProfessor create(SubjectProfessor subjectProfessor);

    SubjectProfessor findByIdRequired(Long subjectId, Long professorId);

    Optional<SubjectProfessor> findById(Long subjectId, Long professorId);

    SubjectProfessor update(SubjectProfessor subjectProfessor);

    void delete(Long subjectId, Long professorId);


    void assignProfessorToSubject(SubjectProfessorCreateRequest createRequest);

    List<SubjectProfessorSimpleDto> getSubjectProfessorSimpleDtosForProfessor();

    List<StudentTestSimpleDto> findTestsForCurrentProfessor();

    List<SubjectProfessorPairingDto> findAllPairings();
}
