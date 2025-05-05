package com.ftn.eobrazovanje.service;

import com.ftn.eobrazovanje.domain.dto.studyProgram.StudyProgramCreateRequest;
import com.ftn.eobrazovanje.domain.entity.StudyProgram;

import java.util.List;

public interface StudyProgramService {

    StudyProgram create(StudyProgram studyProgram);

    StudyProgram findById(Long id);

    List<StudyProgram> findAll();

    void delete(Long id);

    StudyProgram update(StudyProgram studyProgram);

    void createStudyProgram(StudyProgramCreateRequest createRequest);
}
