package com.ftn.eobrazovanje.service.impl;

import com.ftn.eobrazovanje.dao.ProfessorRepository;
import com.ftn.eobrazovanje.domain.entity.user.Professor;
import com.ftn.eobrazovanje.service.ProfessorService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@AllArgsConstructor
@Service
@Transactional
public class ProfessorServiceImpl implements ProfessorService {

    private final ProfessorRepository professorRepository;

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
        return professorRepository.findByEmail(email);
    }

    @Override
    public void delete(Long id) {
        professorRepository.deleteById(id);
    }
}
