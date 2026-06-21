package com.ftn.eobrazovanje.dao;

import com.ftn.eobrazovanje.domain.entity.relational.SubjectStudentTest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SubjectStudentTestRepository extends JpaRepository<SubjectStudentTest, Long> {

    Optional<SubjectStudentTest> findBySubjectStudentAttendance_IdAndSubjectTest_Id(Long studentSubjectAttendanceId, Long testId);

    List<SubjectStudentTest> findAllBySubjectTest_Id(Long subjectTestId);

}
