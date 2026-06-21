package com.ftn.eobrazovanje.service.impl;

import com.ftn.eobrazovanje.dao.ProfessorRepository;
import com.ftn.eobrazovanje.domain.dto.professor.ProfessorDto;
import com.ftn.eobrazovanje.domain.dto.professor.ProfessorTestCreateRequest;
import com.ftn.eobrazovanje.domain.entity.Subject;
import com.ftn.eobrazovanje.domain.entity.relational.SubjectTest;
import com.ftn.eobrazovanje.domain.entity.user.Professor;
import com.ftn.eobrazovanje.service.*;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@AllArgsConstructor
@Service
@Transactional
public class ProfessorServiceImpl implements ProfessorService {

    private final ProfessorRepository professorRepository;
    private final SubjectService subjectService;
    private final SubjectTestService subjectTestService;

    @Override
    public Professor create(Professor professor) {
        return professorRepository.save(professor);
    }

    @Override
    public Professor update(Professor professor) {
        return professorRepository.save(professor);
    }

    @Override
    public Professor getById(Long id) {
        return professorRepository.findById(id).orElse(null);
    }

    @Override
    public Professor getByEmail(String email) {
        return professorRepository.findByUser_Email(email);
    }

    @Override
    public void delete(Long id) {
        professorRepository.deleteById(id);
    }

    @Override
    public void createTest(ProfessorTestCreateRequest professorTestCreateRequest){
        Subject subject = subjectService.getById(professorTestCreateRequest.getSubjectId());
        SubjectTest subjectTest = new SubjectTest(subject, professorTestCreateRequest.getTitle(), professorTestCreateRequest.getDateTime(), false);
        subjectTestService.create(subjectTest);
    }

    @Override
    public List<ProfessorDto> findAllDtos() {
        return professorRepository.findAll().stream()
                .map(professor -> new ProfessorDto(
                        professor.getId(),
                        professor.getUser().getFirstName(),
                        professor.getUser().getLastName(),
                        professor.getUser().getEmail(),
                        professor.getUser().getPhone()))
                .toList();
    }
}
