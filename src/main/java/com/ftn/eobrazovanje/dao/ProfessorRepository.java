package com.ftn.eobrazovanje.dao;

import com.ftn.eobrazovanje.domain.entity.user.Professor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProfessorRepository extends JpaRepository<Professor, Long> {
    Professor findByUser_Email(String email);

}
