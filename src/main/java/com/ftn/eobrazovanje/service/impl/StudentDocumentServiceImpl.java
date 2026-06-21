package com.ftn.eobrazovanje.service.impl;

import com.ftn.eobrazovanje.dao.StudentDocumentRepository;
import com.ftn.eobrazovanje.domain.dto.document.StudentDocumentCreateRequest;
import com.ftn.eobrazovanje.domain.dto.document.StudentDocumentDto;
import com.ftn.eobrazovanje.domain.entity.StudentDocument;
import com.ftn.eobrazovanje.domain.entity.user.Student;
import com.ftn.eobrazovanje.service.StudentDocumentService;
import com.ftn.eobrazovanje.service.StudentService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@Slf4j
@AllArgsConstructor
public class StudentDocumentServiceImpl implements StudentDocumentService {

    private final StudentDocumentRepository studentDocumentRepository;
    private final StudentService studentService;

    @Override
    public StudentDocumentDto create(StudentDocumentCreateRequest createRequest) {
        Student student = studentService.findById(createRequest.getStudentId());
        StudentDocument document = new StudentDocument();
        document.setTitle(createRequest.getTitle());
        document.setDocumentType(createRequest.getDocumentType());
        document.setFilePath(createRequest.getFilePath());
        document.setStudent(student);
        return toDto(studentDocumentRepository.save(document));
    }

    @Override
    public void delete(Long id) {
        studentDocumentRepository.deleteById(id);
    }

    @Override
    public List<StudentDocumentDto> findDtosByStudentId(Long studentId) {
        return studentDocumentRepository.findAllByStudent_Id(studentId).stream()
                .map(this::toDto)
                .toList();
    }

    @Override
    public List<StudentDocumentDto> findDtosForCurrentStudent() {
        return findDtosByStudentId(studentService.getCurrentStudent().getId());
    }

    private StudentDocumentDto toDto(StudentDocument document) {
        return new StudentDocumentDto(document.getId(), document.getTitle(), document.getDocumentType(), document.getFilePath());
    }
}
