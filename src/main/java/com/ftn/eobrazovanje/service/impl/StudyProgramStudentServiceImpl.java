package com.ftn.eobrazovanje.service.impl;

import com.ftn.eobrazovanje.dao.StudyProgramStudentRepository;
import com.ftn.eobrazovanje.domain.dto.subject.SubjectDto;
import com.ftn.eobrazovanje.domain.entity.StudyProgram;
import com.ftn.eobrazovanje.domain.entity.Subject;
import com.ftn.eobrazovanje.domain.entity.relational.StudyProgramStudent;
import com.ftn.eobrazovanje.domain.entity.relational.SubjectStudentAttendance;
import com.ftn.eobrazovanje.domain.entity.user.Student;
import com.ftn.eobrazovanje.exception.StudyProgramStudentNotFound;
import com.ftn.eobrazovanje.service.StudentService;
import com.ftn.eobrazovanje.service.StudyProgramStudentService;
import com.ftn.eobrazovanje.service.SubjectService;
import com.ftn.eobrazovanje.service.SubjectStudentAttendanceService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@Transactional
@AllArgsConstructor
public class StudyProgramStudentServiceImpl implements StudyProgramStudentService {

    private final SubjectService subjectService;
    private final StudyProgramStudentRepository studyProgramStudentRepository;
    private final SubjectStudentAttendanceService subjectStudentAttendanceService;
    private final StudentService studentService;
    @Override
    public StudyProgramStudent create(StudyProgramStudent studyProgramStudent) {
        return studyProgramStudentRepository.save(studyProgramStudent);
    }

    @Override
    public StudyProgramStudent findById(Long studyProgramId, Long studentId) {
        return studyProgramStudentRepository.findById(new StudyProgramStudent.StudyProgramStudentId(studyProgramId, studentId)).orElseThrow(StudyProgramStudentNotFound::new);
    }

    @Override
    public StudyProgramStudent update(StudyProgramStudent studyProgramStudent) {
        return studyProgramStudentRepository.save(studyProgramStudent);
    }

    @Override
    public void delete(Long studyProgramId, Long studentId) {
        studyProgramStudentRepository.deleteById(new StudyProgramStudent.StudyProgramStudentId(studyProgramId, studentId));
    }

    @Override
    public StudyProgramStudent getStudyProgramByStudent(Student student) {
        return studyProgramStudentRepository.findByStudent(student);
    }

    @Override
    public void addStudentToProgram(StudyProgramStudent studyProgramStudent){
        create(studyProgramStudent);
        List<Subject> subjectList = subjectService.getAllForStudyProgram(studyProgramStudent.getStudyProgram());
        List<SubjectStudentAttendance> subjectStudentAttendances = new ArrayList<>();
        subjectList.forEach(subject -> {
            SubjectStudentAttendance subjectStudentAttendance = new SubjectStudentAttendance();
            subjectStudentAttendance.setSubject(subject);
            subjectStudentAttendance.setStudent(studyProgramStudent.getStudent());
            subjectStudentAttendances.add(subjectStudentAttendance);
        });
        subjectStudentAttendanceService.saveAll(subjectStudentAttendances);
    }

    @Override
    public List<SubjectDto> getSubjectDtosForCurrentStudent() {
        Student student = studentService.getCurrentStudent();
        StudyProgram studyProgram = getStudyProgramByStudent(student).getStudyProgram();
        return subjectService.getAllForStudyProgram(studyProgram).stream()
                .map(subject -> new SubjectDto(subject.getId(), subject.getTitle(), subject.getDescription()))
                .toList();
    }
}
