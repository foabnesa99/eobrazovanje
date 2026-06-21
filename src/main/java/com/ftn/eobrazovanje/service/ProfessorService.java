package com.ftn.eobrazovanje.service;

import com.ftn.eobrazovanje.domain.dto.professor.ProfessorDto;
import com.ftn.eobrazovanje.domain.dto.professor.ProfessorTestCreateRequest;
import com.ftn.eobrazovanje.domain.entity.user.Professor;

import java.util.List;

public interface ProfessorService {

    Professor create(Professor professor);

    Professor update(Professor professor);

    Professor getById(Long id);

    Professor getByEmail(String email);

    void delete(Long id);

    void createTest(ProfessorTestCreateRequest professorTestCreateRequest);

    List<ProfessorDto> findAllDtos();
}
