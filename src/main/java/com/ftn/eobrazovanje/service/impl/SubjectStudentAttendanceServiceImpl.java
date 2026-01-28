package com.ftn.eobrazovanje.service.impl;

import com.ftn.eobrazovanje.dao.SubjectStudentAttendanceRepository;
import com.ftn.eobrazovanje.domain.entity.relational.SubjectStudentAttendance;
import com.ftn.eobrazovanje.exception.SubjectStudentAttendanceNotFoundException;
import com.ftn.eobrazovanje.service.SubjectStudentAttendanceService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;

@Service
@Transactional
@Slf4j
@AllArgsConstructor
public class SubjectStudentAttendanceServiceImpl implements SubjectStudentAttendanceService {

    private final SubjectStudentAttendanceRepository subjectStudentAttendanceRepository;

    @Override
    public SubjectStudentAttendance findById(Long id) {
        return subjectStudentAttendanceRepository.findById(id).orElseThrow(SubjectStudentAttendanceNotFoundException::new);
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
    public void delete(Long id) {
        subjectStudentAttendanceRepository.deleteById(id);
    }

    @Override
    public void saveAll(Collection<SubjectStudentAttendance> subjectStudentAttendances) {
        subjectStudentAttendanceRepository.saveAll(subjectStudentAttendances);
    }

    @Override
    public List<SubjectStudentAttendance> findAllBySubjectId(Long subjectId) {
        return subjectStudentAttendanceRepository.findAllBySubject_Id(subjectId);
    }

    @Override
    public List<SubjectStudentAttendance> findAllByStudentId(Long studentId) {
        return subjectStudentAttendanceRepository.findAllByStudent_Id(studentId);
    }

    @Override
    public SubjectStudentAttendance findByStudentIdAndSubjectId(Long studentId, Long subjectId) {
        return subjectStudentAttendanceRepository.findByStudent_IdAndSubject_Id(studentId, subjectId);
    }
}
