package com.ftn.eobrazovanje.controller;

import com.ftn.eobrazovanje.domain.dto.professor.ProfessorTestCreateRequest;
import com.ftn.eobrazovanje.domain.dto.studyProgramSubject.StudyProgramSubjectCreateRequest;
import com.ftn.eobrazovanje.domain.dto.subjectProfessor.SubjectProfessorSimpleDto;
import com.ftn.eobrazovanje.domain.entity.Subject;
import com.ftn.eobrazovanje.service.ProfessorService;
import com.ftn.eobrazovanje.service.SubjectProfessorService;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/professor")
@Tag(name = "Admin Controller", description = "Set of endpoints for administrator management")
@Slf4j
public class ProfessorController {

    private final SubjectProfessorService subjectProfessorService;
    private final ProfessorService professorService;

    public ProfessorController(SubjectProfessorService subjectProfessorService, ProfessorService professorService) {
        this.subjectProfessorService = subjectProfessorService;
        this.professorService = professorService;
    }

    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "User successfully created"),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error"),
    })
    @CrossOrigin(origins = "*")
    @GetMapping("/subjects")
    public ResponseEntity<List<SubjectProfessorSimpleDto>> getSubjectsForProfessor() {
        List<SubjectProfessorSimpleDto> subjectProfessorSimpleDtos = subjectProfessorService.getSubjectProfessorSimpleDtosForProfessor();
        return new ResponseEntity<>(subjectProfessorSimpleDtos, HttpStatus.OK);
    }

    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "User successfully created"),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error"),
    })
    @CrossOrigin(origins = "*")
    @PostMapping(value = "/tests", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<HttpStatus> createTestForProfessor(@RequestBody ProfessorTestCreateRequest professorTestCreateRequest) {
        professorService.createTest(professorTestCreateRequest);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}