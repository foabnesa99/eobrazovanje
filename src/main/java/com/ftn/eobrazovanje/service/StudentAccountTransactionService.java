package com.ftn.eobrazovanje.service;

import com.ftn.eobrazovanje.domain.entity.StudentAccountTransaction;

public interface StudentAccountTransactionService {

    StudentAccountTransaction create(StudentAccountTransaction studentAccountTransaction);

    StudentAccountTransaction findById(Long id);

    StudentAccountTransaction update(StudentAccountTransaction studentAccountTransaction);

    void delete(Long id);


}
