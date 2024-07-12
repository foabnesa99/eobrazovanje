package com.ftn.eobrazovanje.dao;

import com.ftn.eobrazovanje.domain.entity.StudentAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentAccountRepository extends JpaRepository<StudentAccount, Long> {
}
