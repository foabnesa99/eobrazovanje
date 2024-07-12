package com.ftn.eobrazovanje.service;

import com.ftn.eobrazovanje.domain.entity.relational.SubjectStudentAttendance;

public interface SubjectStudentAttendanceService {

    SubjectStudentAttendance findById(Long subjectId, Long studentId);

    SubjectStudentAttendance create(SubjectStudentAttendance subjectStudentAttendance);

    SubjectStudentAttendance update(SubjectStudentAttendance subjectStudentAttendance);

    void delete(Long subjectId, Long studentId);


}
