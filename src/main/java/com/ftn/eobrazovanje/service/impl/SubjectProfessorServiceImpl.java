package com.ftn.eobrazovanje.service.impl;

import com.ftn.eobrazovanje.dao.SubjectProfessorRepository;
import com.ftn.eobrazovanje.domain.dto.subjectProfessor.SubjectProfessorCreateRequest;
import com.ftn.eobrazovanje.domain.dto.subjectProfessor.SubjectProfessorPairingDto;
import com.ftn.eobrazovanje.domain.dto.subjectProfessor.SubjectProfessorSimpleDto;
import com.ftn.eobrazovanje.domain.dto.tests.StudentTestSimpleDto;
import com.ftn.eobrazovanje.domain.entity.Subject;
import com.ftn.eobrazovanje.domain.entity.relational.SubjectProfessor;
import com.ftn.eobrazovanje.domain.entity.relational.SubjectTest;
import com.ftn.eobrazovanje.domain.entity.user.Professor;
import com.ftn.eobrazovanje.domain.entity.user.User;
import com.ftn.eobrazovanje.exception.SubjectProfessorExistsException;
import com.ftn.eobrazovanje.exception.SubjectProfessorNotFoundException;
import com.ftn.eobrazovanje.service.ProfessorService;
import com.ftn.eobrazovanje.service.SubjectProfessorService;
import com.ftn.eobrazovanje.service.SubjectService;
import com.ftn.eobrazovanje.service.SubjectTestService;
import com.ftn.eobrazovanje.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@Slf4j
@AllArgsConstructor
public class SubjectProfessorServiceImpl implements SubjectProfessorService {

    private final SubjectProfessorRepository subjectProfessorRepository;
    private final SubjectService subjectService;
    private final ProfessorService professorService;
    private final UserService userService;
    private final SubjectTestService subjectTestService;
    @Override
    public SubjectProfessor create(SubjectProfessor subjectProfessor) {
        return subjectProfessorRepository.save(subjectProfessor);
    }

    @Override
    public SubjectProfessor findByIdRequired(Long subjectId, Long professorId) {
        return subjectProfessorRepository.findById(new SubjectProfessor.SubjectProfessorId(subjectId, professorId)).orElseThrow(SubjectProfessorNotFoundException::new);
    }

    @Override
    public Optional<SubjectProfessor> findById(Long subjectId, Long professorId) {
        return subjectProfessorRepository.findById(new SubjectProfessor.SubjectProfessorId(subjectId, professorId));
    }

    @Override
    public SubjectProfessor update(SubjectProfessor subjectProfessor) {
        return subjectProfessorRepository.save(subjectProfessor);
    }

    @Override
    public void delete(Long subjectId, Long professorId) {
        subjectProfessorRepository.deleteById(new SubjectProfessor.SubjectProfessorId(subjectId, professorId));
    }

    @Override
    public void assignProfessorToSubject(SubjectProfessorCreateRequest createRequest){
        if(findById(createRequest.getSubjectId(), createRequest.getProfessorId()).isPresent()){
            throw new SubjectProfessorExistsException();
        }
        Subject subject = subjectService.getById(createRequest.getSubjectId());
        Professor professor = professorService.getById(createRequest.getProfessorId());
        create(new SubjectProfessor(professor, subject, createRequest.getProfessorRole()));

    }

    @Override
    public List<SubjectProfessorSimpleDto> getSubjectProfessorSimpleDtosForProfessor(){
        User user = userService.fetchCurrentUser();
        return subjectProfessorRepository.getSubjectProfessorSimpleDtosByUserId(user.getId());
    }

    @Override
    public List<StudentTestSimpleDto> findTestsForCurrentProfessor() {
        List<Long> subjectIds = getSubjectProfessorSimpleDtosForProfessor().stream()
                .map(SubjectProfessorSimpleDto::getId)
                .toList();
        return subjectTestService.findAllBySubjectIds(subjectIds).stream()
                .map(test -> new StudentTestSimpleDto(test.getId(), test.getTitle(), test.getDateTime()))
                .toList();
    }

    @Override
    public List<SubjectProfessorPairingDto> findAllPairings() {
        return subjectProfessorRepository.findAll().stream()
                .map(subjectProfessor -> new SubjectProfessorPairingDto(
                        subjectProfessor.getSubject().getId(),
                        subjectProfessor.getProfessor().getId(),
                        subjectProfessor.getSubject().getTitle(),
                        subjectProfessor.getProfessor().getUser().getFullName(),
                        subjectProfessor.getProfessorRole()))
                .toList();
    }
}
