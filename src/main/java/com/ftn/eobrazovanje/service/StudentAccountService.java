package com.ftn.eobrazovanje.service;

import com.ftn.eobrazovanje.domain.entity.StudentAccount;

public interface StudentAccountService {

    StudentAccount findById(Long id);

    StudentAccount create(StudentAccount studentAccount);

    StudentAccount update(StudentAccount studentAccount);

    void delete(Long id);

}
