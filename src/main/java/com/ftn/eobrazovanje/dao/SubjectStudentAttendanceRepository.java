package com.ftn.eobrazovanje.dao;

import com.ftn.eobrazovanje.domain.entity.relational.SubjectStudentAttendance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubjectStudentAttendanceRepository extends JpaRepository<SubjectStudentAttendance, SubjectStudentAttendance.SubjectStudentAttendanceId> {
}
