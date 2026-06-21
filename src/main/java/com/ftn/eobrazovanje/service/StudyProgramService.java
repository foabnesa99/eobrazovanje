package com.ftn.eobrazovanje.service;

import com.ftn.eobrazovanje.domain.dto.studyProgram.StudyProgramCreateRequest;
import com.ftn.eobrazovanje.domain.dto.studyProgram.StudyProgramDto;
import com.ftn.eobrazovanje.domain.dto.studyProgramSubject.StudyProgramSubjectCreateRequest;
import com.ftn.eobrazovanje.domain.entity.StudyProgram;
import com.ftn.eobrazovanje.domain.entity.user.Student;

import java.util.List;

public interface StudyProgramService {

    StudyProgram create(StudyProgram studyProgram);

    StudyProgram findById(Long id);

    void addStudentToStudyProgram(Long studyProgramId, Student student);

    List<StudyProgram> findAll();

    List<StudyProgramDto> findAllDtos();

    void delete(Long id);

    StudyProgram update(StudyProgram studyProgram);

    void createStudyProgram(StudyProgramCreateRequest createRequest);

    void addSubjectToProgram(StudyProgramSubjectCreateRequest createRequest);

    void updateStudentProgram(Long studyProgramId, Student student);
}
