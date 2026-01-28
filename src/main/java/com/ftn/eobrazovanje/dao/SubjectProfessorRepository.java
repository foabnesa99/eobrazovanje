package com.ftn.eobrazovanje.dao;

import com.ftn.eobrazovanje.domain.dto.subjectProfessor.SubjectProfessorSimpleDto;
import com.ftn.eobrazovanje.domain.entity.relational.SubjectProfessor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubjectProfessorRepository extends JpaRepository<SubjectProfessor, SubjectProfessor.SubjectProfessorId> {

    @Query("""
    select new com.ftn.eobrazovanje.domain.dto.subjectProfessor.SubjectProfessorSimpleDto
    (s.id, s.title, s.description)
    from Subject s
    inner join SubjectProfessor sp on sp.subject.id = s.id
    where sp.professor.id = :userId
""")
    List<SubjectProfessorSimpleDto> getSubjectProfessorSimpleDtosByUserId(Long userId);


}
