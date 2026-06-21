package com.ftn.eobrazovanje.service;

import com.ftn.eobrazovanje.domain.dto.transaction.StudentTransactionCreateRequest;
import com.ftn.eobrazovanje.domain.dto.transaction.StudentTransactionDto;

import java.util.List;

public interface StudentAccountTransactionService {

    void createPayment(StudentTransactionCreateRequest createRequest);

    List<StudentTransactionDto> findDtosForCurrentStudent();

}
