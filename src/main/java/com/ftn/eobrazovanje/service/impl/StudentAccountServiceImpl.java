package com.ftn.eobrazovanje.service.impl;

import com.ftn.eobrazovanje.dao.StudentAccountRepository;
import com.ftn.eobrazovanje.domain.entity.StudentAccount;
import com.ftn.eobrazovanje.exception.StudentAccountNotFoundException;
import com.ftn.eobrazovanje.service.StudentAccountService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@Slf4j
@AllArgsConstructor

public class StudentAccountServiceImpl implements StudentAccountService {

    private final StudentAccountRepository studentAccountRepository;

    @Override
    public StudentAccount findById(Long id) {
        return studentAccountRepository.findById(id).orElseThrow(StudentAccountNotFoundException::new);
    }

    @Override
    public StudentAccount create(StudentAccount studentAccount) {
        return studentAccountRepository.save(studentAccount);
    }

    @Override
    public StudentAccount update(StudentAccount studentAccount) {
        return studentAccountRepository.save(studentAccount);
    }

    @Override
    public void delete(Long id) {
        studentAccountRepository.deleteById(id);
    }
}
