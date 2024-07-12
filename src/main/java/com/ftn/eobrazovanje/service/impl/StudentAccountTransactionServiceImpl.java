package com.ftn.eobrazovanje.service.impl;

import com.ftn.eobrazovanje.dao.StudentAccountTransactionRepository;
import com.ftn.eobrazovanje.domain.entity.StudentAccountTransaction;
import com.ftn.eobrazovanje.exception.StudentAccountTransactionNotFoundException;
import com.ftn.eobrazovanje.service.StudentAccountTransactionService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@Slf4j
@AllArgsConstructor
public class StudentAccountTransactionServiceImpl implements StudentAccountTransactionService {

    private final StudentAccountTransactionRepository studentAccountTransactionRepository;

    @Override
    public StudentAccountTransaction create(StudentAccountTransaction studentAccountTransaction) {
        return studentAccountTransactionRepository.save(studentAccountTransaction);
    }

    @Override
    public StudentAccountTransaction findById(Long id) {
        return studentAccountTransactionRepository.findById(id).orElseThrow(StudentAccountTransactionNotFoundException::new);
    }

    @Override
    public StudentAccountTransaction update(StudentAccountTransaction studentAccountTransaction) {
        return studentAccountTransactionRepository.save(studentAccountTransaction);
    }

    @Override
    public void delete(Long id) {
        studentAccountTransactionRepository.deleteById(id);
    }
}
