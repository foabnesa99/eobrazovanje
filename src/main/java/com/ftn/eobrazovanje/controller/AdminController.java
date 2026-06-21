package com.ftn.eobrazovanje.controller;

import com.ftn.eobrazovanje.domain.common.UserRole;
import com.ftn.eobrazovanje.domain.dto.document.StudentDocumentCreateRequest;
import com.ftn.eobrazovanje.domain.dto.document.StudentDocumentDto;
import com.ftn.eobrazovanje.domain.dto.professor.ProfessorUpdateRequest;
import com.ftn.eobrazovanje.domain.dto.student.StudentUpdateRequest;
import com.ftn.eobrazovanje.domain.dto.studyProgramSubject.StudyProgramSubjectCreateRequest;
import com.ftn.eobrazovanje.domain.dto.studyProgramSubject.StudyProgramSubjectPairingDto;
import com.ftn.eobrazovanje.domain.dto.subject.SubjectCreateRequest;
import com.ftn.eobrazovanje.domain.dto.subjectProfessor.SubjectProfessorCreateRequest;
import com.ftn.eobrazovanje.domain.dto.subjectProfessor.SubjectProfessorPairingDto;
import com.ftn.eobrazovanje.domain.dto.transaction.StudentTransactionCreateRequest;
import com.ftn.eobrazovanje.domain.dto.user.UserCreateRequest;
import com.ftn.eobrazovanje.service.*;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
@Tag(name = "Admin Controller", description = "Set of endpoints for administrator management")
@Slf4j
@AllArgsConstructor
public class AdminController {

    private final SubjectProfessorService subjectProfessorService;
    private final StudyProgramService studyProgramService;
    private final UserService userService;
    private final SubjectService subjectService;
    private final StudentDocumentService studentDocumentService;
    private final StudentAccountTransactionService studentAccountTransactionService;

    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "User successfully created"),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error"),
    })
    @CrossOrigin(origins = "*")
    @PostMapping(value = "/student/create", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> createStudentUser(@RequestBody UserCreateRequest createRequest) {
        createRequest.setRole(UserRole.STUDENT);
        userService.createUser(createRequest);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "User successfully created"),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error"),
    })
    @CrossOrigin(origins = "*")
    @PostMapping(value = "/professor/create", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> createProfessorUser(@RequestBody UserCreateRequest createRequest) {
        createRequest.setRole(UserRole.PROFESSOR);
        userService.createUser(createRequest);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Subject successfully created"),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error"),
    })
    @CrossOrigin(origins = "*")
    @PostMapping(value = "/subject/create", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> createSubject(@RequestBody SubjectCreateRequest createRequest) {
        subjectService.createSubject(createRequest);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Subject Professor successfully created"),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error"),
    })
    @CrossOrigin(origins = "*")
    @PostMapping(value = "/subject-professor/create", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> createSubjectProfessor(@RequestBody SubjectProfessorCreateRequest createRequest) {
        subjectProfessorService.assignProfessorToSubject(createRequest);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @CrossOrigin(origins = "*")
    @GetMapping(value = "/subject-professor")
    public ResponseEntity<List<SubjectProfessorPairingDto>> getSubjectProfessorPairings() {
        return new ResponseEntity<>(subjectProfessorService.findAllPairings(), HttpStatus.OK);
    }

    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Subject added to study program"),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error"),
    })
    @CrossOrigin(origins = "*")
    @PostMapping(value = "/program-subject/create", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> addSubjectToProgram(@RequestBody StudyProgramSubjectCreateRequest createRequest) {
        studyProgramService.addSubjectToProgram(createRequest);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @CrossOrigin(origins = "*")
    @GetMapping(value = "/program-subject")
    public ResponseEntity<List<StudyProgramSubjectPairingDto>> getProgramSubjectPairings() {
        return new ResponseEntity<>(subjectService.findAllProgramPairings(), HttpStatus.OK);
    }

    @CrossOrigin(origins = "*")
    @PostMapping(value = "/document/create", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<StudentDocumentDto> createDocument(@RequestBody StudentDocumentCreateRequest createRequest) {
        return new ResponseEntity<>(studentDocumentService.create(createRequest), HttpStatus.CREATED);
    }

    @CrossOrigin(origins = "*")
    @DeleteMapping(value = "/document/{id}")
    public ResponseEntity<Void> deleteDocument(@PathVariable Long id) {
        studentDocumentService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @CrossOrigin(origins = "*")
    @GetMapping(value = "/documents/{studentId}")
    public ResponseEntity<List<StudentDocumentDto>> getDocumentsForStudent(@PathVariable Long studentId) {
        return new ResponseEntity<>(studentDocumentService.findDtosByStudentId(studentId), HttpStatus.OK);
    }

    @CrossOrigin(origins = "*")
    @PostMapping(value = "/payment/create", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> createPayment(@RequestBody StudentTransactionCreateRequest createRequest) {
        studentAccountTransactionService.createPayment(createRequest);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @CrossOrigin(origins = "*")
    @DeleteMapping(value = "/student/{id}")
    public ResponseEntity<Void> deleteStudent(@PathVariable Long id) {
        userService.deleteStudent(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @CrossOrigin(origins = "*")
    @DeleteMapping(value = "/professor/{id}")
    public ResponseEntity<Void> deleteProfessor(@PathVariable Long id) {
        userService.deleteProfessor(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @CrossOrigin(origins = "*")
    @DeleteMapping(value = "/subject/{id}")
    public ResponseEntity<Void> deleteSubject(@PathVariable Long id) {
        subjectService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @CrossOrigin(origins = "*")
    @DeleteMapping(value = "/study-program/{id}")
    public ResponseEntity<Void> deleteStudyProgram(@PathVariable Long id) {
        studyProgramService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @CrossOrigin(origins = "*")
    @DeleteMapping(value = "/subject-professor/{subjectId}/{professorId}")
    public ResponseEntity<Void> deleteSubjectProfessor(@PathVariable Long subjectId, @PathVariable Long professorId) {
        subjectProfessorService.delete(subjectId, professorId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @CrossOrigin(origins = "*")
    @DeleteMapping(value = "/program-subject/{subjectId}")
    public ResponseEntity<Void> deleteProgramSubject(@PathVariable Long subjectId) {
        subjectService.removeSubjectFromProgram(subjectId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @CrossOrigin(origins = "*")
    @PutMapping(value = "/student/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> updateStudent(@PathVariable Long id, @RequestBody StudentUpdateRequest request) {
        userService.updateStudent(id, request);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @CrossOrigin(origins = "*")
    @PutMapping(value = "/professor/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> updateProfessor(@PathVariable Long id, @RequestBody ProfessorUpdateRequest request) {
        userService.updateProfessor(id, request);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
