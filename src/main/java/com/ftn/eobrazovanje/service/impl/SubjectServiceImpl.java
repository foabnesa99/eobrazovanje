package com.ftn.eobrazovanje.service.impl;

import com.ftn.eobrazovanje.dao.SubjectRepository;
import com.ftn.eobrazovanje.domain.dto.studyProgramSubject.StudyProgramSubjectCreateRequest;
import com.ftn.eobrazovanje.domain.dto.studyProgramSubject.StudyProgramSubjectPairingDto;
import com.ftn.eobrazovanje.domain.dto.subject.SubjectCreateRequest;
import com.ftn.eobrazovanje.domain.dto.subject.SubjectDto;
import com.ftn.eobrazovanje.domain.entity.StudyProgram;
import com.ftn.eobrazovanje.domain.entity.Subject;
import com.ftn.eobrazovanje.exception.SubjectNotFoundException;
import com.ftn.eobrazovanje.service.SubjectService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
@Transactional
public class SubjectServiceImpl implements SubjectService {

    private final SubjectRepository subjectRepository;

    public SubjectServiceImpl(SubjectRepository subjectRepository) {
        this.subjectRepository = subjectRepository;
    }

    @Override
    public Subject getById(Long id) {
        return subjectRepository.findById(id).orElseThrow(SubjectNotFoundException::new);
    }

    @Override
    public Subject create(Subject subject) {
       return subjectRepository.save(subject);
    }

    @Override
    public Subject update(Subject subject) {
        return subjectRepository.save(subject);
    }

    @Override
    public void delete(Long id) {
        subjectRepository.deleteById(id);
    }

    @Override
    public void createSubject(SubjectCreateRequest createRequest){
        Subject subject = new Subject(createRequest.getTitle(), createRequest.getDescription());
        create(subject);
    }

    @Override
    public List<SubjectDto> findAllDtos() {
        return subjectRepository.findAll().stream()
                .map(subject -> new SubjectDto(subject.getId(), subject.getTitle(), subject.getDescription()))
                .toList();
    }

    @Override
    public List<StudyProgramSubjectPairingDto> findAllProgramPairings() {
        return subjectRepository.findAll().stream()
                .filter(subject -> subject.getStudyProgram() != null)
                .map(subject -> new StudyProgramSubjectPairingDto(
                        subject.getId(),
                        subject.getTitle(),
                        subject.getStudyProgram().getCode(),
                        subject.getStudyProgram().getName()))
                .toList();
    }

    @Override
    public List<Subject> getAll() {
        return subjectRepository.findAll();
    }

    @Override
    public List<Subject> getAllForStudyProgram(StudyProgram studyProgram) {
        return subjectRepository.findAllByStudyProgram(studyProgram);
    }

    @Override
    public void addSubjectToProgram(StudyProgram studyProgram, StudyProgramSubjectCreateRequest createRequest) {
        Subject subject = getById(createRequest.getSubjectId());
        subject.setStudyProgram(studyProgram);
        update(subject);
    }
}
