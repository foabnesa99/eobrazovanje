package com.ftn.eobrazovanje.dao;

import com.ftn.eobrazovanje.domain.entity.StudentDocument;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentDocumentRepository extends JpaRepository<StudentDocument, Long> {

    List<StudentDocument> findAllByStudent_Id(Long studentId);
}
