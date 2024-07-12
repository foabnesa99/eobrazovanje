package com.ftn.eobrazovanje.dao;

import com.ftn.eobrazovanje.domain.entity.relational.SubjectStudentTest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubjectStudentTestRepository extends JpaRepository<SubjectStudentTest, Long> {
}
