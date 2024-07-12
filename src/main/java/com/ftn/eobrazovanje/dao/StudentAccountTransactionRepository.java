package com.ftn.eobrazovanje.dao;

import com.ftn.eobrazovanje.domain.entity.StudentAccountTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentAccountTransactionRepository extends JpaRepository<StudentAccountTransaction, Long> {
}
