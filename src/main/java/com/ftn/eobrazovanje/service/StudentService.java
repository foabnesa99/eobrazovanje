package com.ftn.eobrazovanje.service;

import com.ftn.eobrazovanje.domain.entity.user.Student;

public interface StudentService {

    Student findById(Long id);

    Student findByEmail(String email);

    Student create(Student student);

    Student update(Student student);

    void delete(Long id);


}
