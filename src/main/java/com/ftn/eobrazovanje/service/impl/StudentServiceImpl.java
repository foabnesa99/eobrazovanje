package com.ftn.eobrazovanje.service.impl;

import com.ftn.eobrazovanje.dao.StudentRepository;
import com.ftn.eobrazovanje.dao.StudyProgramStudentRepository;
import com.ftn.eobrazovanje.domain.dto.security.CustomUserDetails;
import com.ftn.eobrazovanje.domain.dto.student.StudentDto;
import com.ftn.eobrazovanje.domain.entity.relational.StudyProgramStudent;
import com.ftn.eobrazovanje.domain.entity.user.Student;
import com.ftn.eobrazovanje.service.StudentService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
@Transactional
@AllArgsConstructor
public class StudentServiceImpl implements StudentService {

    protected final StudentRepository studentRepository;
    private final StudyProgramStudentRepository studyProgramStudentRepository;

    @Override
    public Student findById(Long id) {
        return studentRepository.findById(id).orElse(null);
    }

    @Override
    public Student findByEmail(String email) {
        return studentRepository.findByUser_Email(email);
    }

    @Override
    public Student getCurrentStudent() {
        CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return findByEmail(userDetails.getUsername());
    }

    @Override
    public Student create(Student student) {
        return studentRepository.save(student);
    }

    @Override
    public Student update(Student student) {
        return studentRepository.save(student);
    }

    @Override
    public void delete(Long id) {
        studentRepository.deleteById(id);
    }

    @Override
    public List<StudentDto> findAllDtos() {
        return studentRepository.findAll().stream()
                .map(student -> {
                    StudyProgramStudent association = studyProgramStudentRepository.findByStudent(student);
                    Long studyProgramId = association != null ? association.getStudyProgram().getId() : null;
                    return new StudentDto(
                            student.getId(),
                            student.getUser().getFirstName(),
                            student.getUser().getLastName(),
                            student.getUser().getEmail(),
                            student.getUser().getPhone(),
                            studyProgramId);
                })
                .toList();
    }
}
