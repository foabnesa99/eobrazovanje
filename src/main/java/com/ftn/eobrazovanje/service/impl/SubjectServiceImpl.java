package com.ftn.eobrazovanje.service.impl;

import com.ftn.eobrazovanje.dao.SubjectRepository;
import com.ftn.eobrazovanje.domain.dto.studyProgramSubject.StudyProgramSubjectCreateRequest;
import com.ftn.eobrazovanje.domain.dto.subject.SubjectCreateRequest;
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
    public List<Subject> getAll() {
        return subjectRepository.findAll();
    }

    @Override
    public void addSubjectToProgram(StudyProgram studyProgram, StudyProgramSubjectCreateRequest createRequest) {
        Subject subject = getById(createRequest.getSubjectId());
        subject.setStudyProgram(studyProgram);
        update(subject);
    }
}
