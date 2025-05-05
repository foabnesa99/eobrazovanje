package com.ftn.eobrazovanje.controller;

import com.ftn.eobrazovanje.domain.common.UserRole;
import com.ftn.eobrazovanje.domain.dto.studyProgramSubject.StudyProgramSubjectCreateRequest;
import com.ftn.eobrazovanje.domain.dto.subject.SubjectCreateRequest;
import com.ftn.eobrazovanje.domain.dto.subjectProfessor.SubjectProfessorCreateRequest;
import com.ftn.eobrazovanje.domain.dto.user.UserCreateRequest;
import com.ftn.eobrazovanje.domain.entity.StudyProgram;
import com.ftn.eobrazovanje.domain.entity.Subject;
import com.ftn.eobrazovanje.domain.entity.relational.SubjectProfessor;
import com.ftn.eobrazovanje.domain.entity.user.User;
import com.ftn.eobrazovanje.service.StudyProgramService;
import com.ftn.eobrazovanje.service.SubjectProfessorService;
import com.ftn.eobrazovanje.service.SubjectService;
import com.ftn.eobrazovanje.service.UserService;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/admin")
@Tag(name = "Admin Controller", description = "Set of endpoints for administrator management")
@Slf4j
public class AdminController {

    private final SubjectProfessorService subjectProfessorService;
    private final StudyProgramService studyProgramService;
    private final UserService userService;
    private final SubjectService subjectService;

    public AdminController(SubjectProfessorService subjectProfessorService, StudyProgramService studyProgramService, UserService userService, SubjectService subjectService) {
        this.subjectProfessorService = subjectProfessorService;
        this.studyProgramService = studyProgramService;
        this.userService = userService;
        this.subjectService = subjectService;
    }


    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "User successfully created"),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error"),
    })
    @CrossOrigin(origins = "*")
    @PostMapping(value = "/student/create", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> createStudentUser(@RequestBody UserCreateRequest createRequest) {
        createRequest.setRole(UserRole.STUDENT);
        User user = userService.createUser(createRequest);
        return new ResponseEntity<>(new User(), HttpStatus.CREATED);
    }

    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "User successfully created"),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error"),
    })
    @CrossOrigin(origins = "*")
    @PostMapping(value = "/professor/create", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> createProfessorUser(@RequestBody UserCreateRequest createRequest) {
        createRequest.setRole(UserRole.PROFESSOR);
        User user = userService.createUser(createRequest);
        return new ResponseEntity<>(new User(), HttpStatus.CREATED);
    }

    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Subject successfully created"),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error"),
    })
    @CrossOrigin(origins = "*")
    @PostMapping(value = "/subject/create", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Subject> createSubject(@RequestBody SubjectCreateRequest createRequest) {
        subjectService.createSubject(createRequest);
        return new ResponseEntity<>(new Subject(), HttpStatus.CREATED);
    }

    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Subject Professor successfully created"),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error"),
    })
    @CrossOrigin(origins = "*")
    @PostMapping(value = "/subject-professor/create", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<SubjectProfessor> createSubjectProfessor(@RequestBody SubjectProfessorCreateRequest createRequest) {
        subjectProfessorService.assignProfessorToSubject(createRequest);
        return new ResponseEntity<>(new SubjectProfessor(), HttpStatus.CREATED);
    }

    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Subject Professor successfully created"),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error"),
    })
    @CrossOrigin(origins = "*")
    @PostMapping(value = "/program-subject/create", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Subject> addSubjectToProgram(@RequestBody StudyProgramSubjectCreateRequest createRequest) {
        StudyProgram studyProgram = studyProgramService.findById(createRequest.getStudyProgramId());
        subjectService.addSubjectToProgram(studyProgram, createRequest);
        return new ResponseEntity<>(new Subject(), HttpStatus.CREATED);
    }




}
