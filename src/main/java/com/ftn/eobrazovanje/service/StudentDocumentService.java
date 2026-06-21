package com.ftn.eobrazovanje.service;

import com.ftn.eobrazovanje.domain.dto.document.StudentDocumentCreateRequest;
import com.ftn.eobrazovanje.domain.dto.document.StudentDocumentDto;

import java.util.List;

public interface StudentDocumentService {

    StudentDocumentDto create(StudentDocumentCreateRequest createRequest);

    void delete(Long id);

    List<StudentDocumentDto> findDtosByStudentId(Long studentId);

    List<StudentDocumentDto> findDtosForCurrentStudent();
}
