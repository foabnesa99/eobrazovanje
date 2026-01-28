package com.ftn.eobrazovanje.dao;

import com.ftn.eobrazovanje.domain.entity.relational.StudyProgramStudent;
import com.ftn.eobrazovanje.domain.entity.user.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudyProgramStudentRepository extends JpaRepository<StudyProgramStudent, StudyProgramStudent.StudyProgramStudentId> {

    StudyProgramStudent findByStudent(Student student);

}
