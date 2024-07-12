package com.ftn.eobrazovanje.dao;

import com.ftn.eobrazovanje.domain.entity.relational.SubjectProfessor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubjectProfessorRepository extends JpaRepository<SubjectProfessor, SubjectProfessor.SubjectProfessorId> {
}
