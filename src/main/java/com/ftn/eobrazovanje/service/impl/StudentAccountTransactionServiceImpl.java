package com.ftn.eobrazovanje.service.impl;

import com.ftn.eobrazovanje.dao.StudentAccountTransactionRepository;
import com.ftn.eobrazovanje.domain.dto.transaction.StudentTransactionCreateRequest;
import com.ftn.eobrazovanje.domain.dto.transaction.StudentTransactionDto;
import com.ftn.eobrazovanje.domain.entity.StudentAccount;
import com.ftn.eobrazovanje.domain.entity.StudentAccountTransaction;
import com.ftn.eobrazovanje.service.StudentAccountService;
import com.ftn.eobrazovanje.service.StudentAccountTransactionService;
import com.ftn.eobrazovanje.service.StudentService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
@Slf4j
@AllArgsConstructor
public class StudentAccountTransactionServiceImpl implements StudentAccountTransactionService {

    private final StudentAccountTransactionRepository studentAccountTransactionRepository;
    private final StudentAccountService studentAccountService;
    private final StudentService studentService;

    @Override
    public void createPayment(StudentTransactionCreateRequest createRequest) {
        StudentAccount account = studentAccountService.findByStudentId(createRequest.getStudentId());
        StudentAccountTransaction transaction = new StudentAccountTransaction();
        transaction.setAmount(createRequest.getAmount());
        transaction.setTransactionType(createRequest.getTransactionType());
        transaction.setTransactionTimestamp(LocalDateTime.now());
        transaction.setStudentAccount(account);
        studentAccountTransactionRepository.save(transaction);
        account.setBalance(account.getBalance() + createRequest.getAmount());
        studentAccountService.update(account);
    }

    @Override
    public List<StudentTransactionDto> findDtosForCurrentStudent() {
        StudentAccount account = studentAccountService.findByStudentId(studentService.getCurrentStudent().getId());
        return studentAccountTransactionRepository.findAllByStudentAccount_Id(account.getId()).stream()
                .map(transaction -> new StudentTransactionDto(
                        transaction.getId(),
                        transaction.getTransactionTimestamp() != null ? transaction.getTransactionTimestamp().toString() : "",
                        transaction.getTransactionType() != null ? transaction.getTransactionType().name() : "",
                        transaction.getAmount()))
                .toList();
    }
}
