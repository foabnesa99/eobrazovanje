package com.ftn.eobrazovanje.service;

import com.ftn.eobrazovanje.domain.entity.relational.SubjectStudentAttendance;

import java.util.Collection;
import java.util.List;

public interface SubjectStudentAttendanceService {

    SubjectStudentAttendance findById(Long studentId);

    SubjectStudentAttendance create(SubjectStudentAttendance subjectStudentAttendance);

    SubjectStudentAttendance update(SubjectStudentAttendance subjectStudentAttendance);

    void delete(Long studentId);


    void saveAll(Collection<SubjectStudentAttendance> subjectStudentAttendances);

    List<SubjectStudentAttendance> findAllBySubjectId(Long subjectId);
    List<SubjectStudentAttendance> findAllByStudentId(Long studentId);

    SubjectStudentAttendance findByStudentIdAndSubjectId(Long studentId, Long subjectId);
}
