package com.ftn.eobrazovanje.service.impl;

import com.ftn.eobrazovanje.dao.SubjectStudentAttendanceRepository;
import com.ftn.eobrazovanje.domain.entity.relational.SubjectStudentAttendance;
import com.ftn.eobrazovanje.exception.SubjectStudentAttendanceNotFoundException;
import com.ftn.eobrazovanje.service.SubjectStudentAttendanceService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@Slf4j
@AllArgsConstructor
public class SubjectStudentAttendanceServiceImpl implements SubjectStudentAttendanceService {

    private final SubjectStudentAttendanceRepository subjectStudentAttendanceRepository;

    @Override
    public SubjectStudentAttendance findById(Long subjectId, Long studentId) {
        return subjectStudentAttendanceRepository.findById(new SubjectStudentAttendance.SubjectStudentAttendanceId(subjectId, studentId)).orElseThrow(SubjectStudentAttendanceNotFoundException::new);
    }

    @Override
    public SubjectStudentAttendance create(SubjectStudentAttendance subjectStudentAttendance) {
        return subjectStudentAttendanceRepository.save(subjectStudentAttendance);
    }

    @Override
    public SubjectStudentAttendance update(SubjectStudentAttendance subjectStudentAttendance) {
        return subjectStudentAttendanceRepository.save(subjectStudentAttendance);
    }

    @Override
    public void delete(Long subjectId, Long studentId) {
        subjectStudentAttendanceRepository.deleteById(new SubjectStudentAttendance.SubjectStudentAttendanceId(subjectId, studentId));
    }
}
