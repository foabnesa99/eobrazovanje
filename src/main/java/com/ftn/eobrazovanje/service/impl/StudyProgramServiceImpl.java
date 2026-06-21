package com.ftn.eobrazovanje.service.impl;

import com.ftn.eobrazovanje.dao.StudyProgramRepository;
import com.ftn.eobrazovanje.domain.dto.studyProgram.StudyProgramCreateRequest;
import com.ftn.eobrazovanje.domain.dto.studyProgram.StudyProgramDto;
import com.ftn.eobrazovanje.domain.dto.studyProgramSubject.StudyProgramSubjectCreateRequest;
import com.ftn.eobrazovanje.domain.entity.StudyProgram;
import com.ftn.eobrazovanje.domain.entity.relational.StudyProgramStudent;
import com.ftn.eobrazovanje.domain.entity.user.Student;
import com.ftn.eobrazovanje.exception.StudyProgramNotFound;
import com.ftn.eobrazovanje.service.StudyProgramService;
import com.ftn.eobrazovanje.service.StudyProgramStudentService;
import com.ftn.eobrazovanje.service.SubjectService;
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
    private final StudyProgramStudentService studyProgramStudentService;
    private final SubjectService subjectService;

    @Override
    public StudyProgram create(StudyProgram studyProgram) {
        return studyProgramRepository.save(studyProgram);
    }

    @Override
    public StudyProgram findById(Long id) {
        return studyProgramRepository.findById(id).orElseThrow(StudyProgramNotFound::new);
    }

    @Override
    public void addStudentToStudyProgram(Long studyProgramId, Student student) {
        StudyProgram studyProgram = findById(studyProgramId);
        studyProgramStudentService.addStudentToProgram(new StudyProgramStudent(student, studyProgram));
    }

    @Override
    public List<StudyProgram> findAll() {
        return studyProgramRepository.findAll();
    }

    @Override
    public List<StudyProgramDto> findAllDtos() {
        return studyProgramRepository.findAll().stream()
                .map(studyProgram -> new StudyProgramDto(
                        studyProgram.getId(),
                        studyProgram.getName(),
                        studyProgram.getDescription(),
                        studyProgram.getCode()))
                .toList();
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

    @Override
    public void addSubjectToProgram(StudyProgramSubjectCreateRequest createRequest) {
        StudyProgram studyProgram = findById(createRequest.getStudyProgramId());
        subjectService.addSubjectToProgram(studyProgram, createRequest);
    }

    @Override
    public void updateStudentProgram(Long studyProgramId, Student student) {
        StudyProgramStudent existing = studyProgramStudentService.getStudyProgramByStudent(student);
        if (existing != null) {
            if (existing.getStudyProgram().getId().equals(studyProgramId)) {
                return;
            }
            studyProgramStudentService.delete(existing.getStudyProgram().getId(), student.getId());
        }
        addStudentToStudyProgram(studyProgramId, student);
    }
}
