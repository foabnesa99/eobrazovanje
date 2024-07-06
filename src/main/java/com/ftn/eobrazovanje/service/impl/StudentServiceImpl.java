package com.ftn.eobrazovanje.service.impl;

import com.ftn.eobrazovanje.dao.StudentRepository;
import com.ftn.eobrazovanje.domain.entity.user.Student;
import com.ftn.eobrazovanje.service.StudentService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@Transactional
@AllArgsConstructor
public class StudentServiceImpl implements StudentService {

    protected final StudentRepository studentRepository;

    @Override
    public Student findById(Long id) {
        return studentRepository.findById(id).orElse(null);
    }

    @Override
    public Student findByEmail(String email) {
        return studentRepository.findByUser_Email(email);
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
}
