package com.ftn.eobrazovanje.controller;

import com.ftn.eobrazovanje.domain.dto.attendance.SubjectAttendanceDto;
import com.ftn.eobrazovanje.domain.dto.document.StudentDocumentDto;
import com.ftn.eobrazovanje.domain.dto.tests.StudentTestSimpleDto;
import com.ftn.eobrazovanje.domain.dto.transaction.StudentTransactionDto;
import com.ftn.eobrazovanje.service.StudentAccountTransactionService;
import com.ftn.eobrazovanje.service.StudentDocumentService;
import com.ftn.eobrazovanje.service.SubjectStudentAttendanceService;
import com.ftn.eobrazovanje.service.SubjectStudentTestService;
import com.ftn.eobrazovanje.service.SubjectTestService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/student")
@Tag(name = "Student Controller", description = "Set of endpoints for the current student")
@Slf4j
@AllArgsConstructor
public class StudentController {

    private final SubjectTestService subjectTestService;
    private final SubjectStudentTestService subjectStudentTestService;
    private final StudentDocumentService studentDocumentService;
    private final StudentAccountTransactionService studentAccountTransactionService;
    private final SubjectStudentAttendanceService subjectStudentAttendanceService;

    @CrossOrigin(origins = "*")
    @GetMapping("/tests")
    public ResponseEntity<List<StudentTestSimpleDto>> getTestsForStudent() {
        return new ResponseEntity<>(subjectTestService.getAvailableTestsForCurrentStudent(), HttpStatus.OK);
    }

    @CrossOrigin(origins = "*")
    @PutMapping(value = "/test/assign")
    public ResponseEntity<Void> assignStudentToTest(@RequestParam Long testId) {
        subjectStudentTestService.assignCurrentStudentToTest(testId);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    @CrossOrigin(origins = "*")
    @GetMapping("/documents")
    public ResponseEntity<List<StudentDocumentDto>> getDocumentsForCurrentStudent() {
        return new ResponseEntity<>(studentDocumentService.findDtosForCurrentStudent(), HttpStatus.OK);
    }

    @CrossOrigin(origins = "*")
    @GetMapping("/payments")
    public ResponseEntity<List<StudentTransactionDto>> getPaymentsForCurrentStudent() {
        return new ResponseEntity<>(studentAccountTransactionService.findDtosForCurrentStudent(), HttpStatus.OK);
    }

    @CrossOrigin(origins = "*")
    @GetMapping("/exam-results")
    public ResponseEntity<List<SubjectAttendanceDto>> getExamResultsForCurrentStudent() {
        return new ResponseEntity<>(subjectStudentAttendanceService.findDtosForCurrentStudent(), HttpStatus.OK);
    }

}
