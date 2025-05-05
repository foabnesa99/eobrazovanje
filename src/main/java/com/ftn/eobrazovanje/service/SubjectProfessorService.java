package com.ftn.eobrazovanje.service;

import com.ftn.eobrazovanje.domain.dto.subjectProfessor.SubjectProfessorCreateRequest;
import com.ftn.eobrazovanje.domain.entity.relational.SubjectProfessor;

import java.util.Optional;

public interface SubjectProfessorService {

    SubjectProfessor create(SubjectProfessor subjectProfessor);

    SubjectProfessor findByIdRequired(Long subjectId, Long professorId);

    Optional<SubjectProfessor> findById(Long subjectId, Long professorId);

    SubjectProfessor update(SubjectProfessor subjectProfessor);

    void delete(Long subjectId, Long professorId);


    void assignProfessorToSubject(SubjectProfessorCreateRequest createRequest);
}
