package com.ftn.eobrazovanje.service;


import com.ftn.eobrazovanje.domain.dto.studyProgramSubject.StudyProgramSubjectCreateRequest;
import com.ftn.eobrazovanje.domain.dto.subject.SubjectCreateRequest;
import com.ftn.eobrazovanje.domain.dto.subject.SubjectDto;
import com.ftn.eobrazovanje.domain.entity.StudyProgram;
import com.ftn.eobrazovanje.domain.entity.Subject;

import java.util.List;

public interface SubjectService {

    com.ftn.eobrazovanje.domain.entity.Subject getById(Long id);

    Subject create(Subject subject);

    Subject update(Subject subject);

    void delete(Long id);

    void createSubject(SubjectCreateRequest createRequest);

    List<Subject> getAll();

    void addSubjectToProgram(StudyProgram studyProgram, StudyProgramSubjectCreateRequest createRequest);
}
