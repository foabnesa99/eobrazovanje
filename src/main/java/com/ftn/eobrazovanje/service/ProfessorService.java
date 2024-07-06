package com.ftn.eobrazovanje.service;

import com.ftn.eobrazovanje.domain.entity.user.Professor;

public interface ProfessorService {

    Professor create(Professor professor);

    Professor update(Professor professor);

    Professor getById(Long id);

    Professor getByEmail(String email);

    void delete(Long id);


}
