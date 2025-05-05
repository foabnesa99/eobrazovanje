package com.ftn.eobrazovanje.service.impl;

import com.ftn.eobrazovanje.dao.StudyProgramRepository;
import com.ftn.eobrazovanje.domain.dto.studyProgram.StudyProgramCreateRequest;
import com.ftn.eobrazovanje.domain.entity.StudyProgram;
import com.ftn.eobrazovanje.exception.StudyProgramNotFound;
import com.ftn.eobrazovanje.service.StudyProgramService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@AllArgsConstructor
@Slf4j
@Transactional
@Service
public class StudyProgramServiceImpl implements StudyProgramService {

    private final StudyProgramRepository studyProgramRepository;

    @Override
    public StudyProgram create(StudyProgram studyProgram) {
        return studyProgramRepository.save(studyProgram);
    }

    @Override
    public StudyProgram findById(Long id) {
        return studyProgramRepository.findById(id).orElseThrow(StudyProgramNotFound::new);
    }

    @Override
    public List<StudyProgram> findAll() {
        return studyProgramRepository.findAll();
    }

    @Override
    public void delete(Long id) {
        studyProgramRepository.deleteById(id);
    }

    @Override
    public StudyProgram update(StudyProgram studyProgram) {
        return studyProgramRepository.save(studyProgram);
    }

    @Override
    public void createStudyProgram(StudyProgramCreateRequest createRequest) {
        StudyProgram studyProgram = new StudyProgram();
        studyProgram.setName(createRequest.getName());
        studyProgram.setDescription(createRequest.getDescription());
        studyProgram.setCode(createRequest.getCode());
        studyProgramRepository.save(studyProgram);
    }
}
