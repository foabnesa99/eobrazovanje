package com.ftn.eobrazovanje.service;

import com.ftn.eobrazovanje.domain.entity.relational.StudyProgramStudent;
import com.ftn.eobrazovanje.domain.entity.user.Student;

public interface StudyProgramStudentService {

    StudyProgramStudent create(StudyProgramStudent studyProgramStudent);

    StudyProgramStudent findById(Long studyProgramId, Long studentId);

    StudyProgramStudent update(StudyProgramStudent studyProgramStudent);

    void delete(Long studyProgramId, Long studentId);

    StudyProgramStudent getStudyProgramByStudent(Student student);

    void addStudentToProgram(StudyProgramStudent studyProgramStudent);
}
