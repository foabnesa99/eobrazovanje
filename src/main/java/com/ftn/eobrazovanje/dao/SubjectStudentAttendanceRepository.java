package com.ftn.eobrazovanje.dao;

import com.ftn.eobrazovanje.domain.entity.relational.SubjectStudentAttendance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubjectStudentAttendanceRepository extends JpaRepository<SubjectStudentAttendance, Long> {

    List<SubjectStudentAttendance> findAllBySubject_Id(Long subjectId);

    List<SubjectStudentAttendance> findAllByStudent_Id(Long studentId);

    SubjectStudentAttendance findByStudent_IdAndSubject_Id(Long studentId, Long subjectId);

    void deleteAllByStudent_Id(Long studentId);

}
