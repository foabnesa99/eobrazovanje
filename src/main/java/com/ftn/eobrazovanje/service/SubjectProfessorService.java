package com.ftn.eobrazovanje.service;

import com.ftn.eobrazovanje.domain.entity.relational.SubjectProfessor;

public interface SubjectProfessorService {

    SubjectProfessor create(SubjectProfessor subjectProfessor);

    SubjectProfessor findById(Long subjectId, Long professorId);

    SubjectProfessor update(SubjectProfessor subjectProfessor);

    void delete(Long subjectId, Long professorId);


}
