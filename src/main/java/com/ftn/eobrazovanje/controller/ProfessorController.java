package com.ftn.eobrazovanje.controller;

import com.ftn.eobrazovanje.domain.dto.professor.ProfessorTestCreateRequest;
import com.ftn.eobrazovanje.domain.dto.subjectProfessor.SubjectProfessorSimpleDto;
import com.ftn.eobrazovanje.domain.dto.tests.GradeTestRequest;
import com.ftn.eobrazovanje.domain.dto.tests.StudentTestGradeDto;
import com.ftn.eobrazovanje.domain.dto.tests.StudentTestSimpleDto;
import com.ftn.eobrazovanje.service.ProfessorService;
import com.ftn.eobrazovanje.service.SubjectProfessorService;
import com.ftn.eobrazovanje.service.SubjectStudentTestService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/professor")
@Tag(name = "Professor Controller", description = "Set of endpoints for professor management")
@Slf4j
@AllArgsConstructor
public class ProfessorController {

    private final SubjectProfessorService subjectProfessorService;
    private final ProfessorService professorService;
    private final SubjectStudentTestService subjectStudentTestService;

    @CrossOrigin(origins = "*")
    @GetMapping("/subjects")
    public ResponseEntity<List<SubjectProfessorSimpleDto>> getSubjectsForProfessor() {
        return new ResponseEntity<>(subjectProfessorService.getSubjectProfessorSimpleDtosForProfessor(), HttpStatus.OK);
    }

    @CrossOrigin(origins = "*")
    @PostMapping(value = "/tests", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> createTestForProfessor(@RequestBody ProfessorTestCreateRequest professorTestCreateRequest) {
        professorService.createTest(professorTestCreateRequest);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @CrossOrigin(origins = "*")
    @PutMapping(value = "/test/grade", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> gradeTest(@RequestBody GradeTestRequest gradeRequest) {
        subjectStudentTestService.gradeTest(gradeRequest);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @CrossOrigin(origins = "*")
    @GetMapping("/test/{testId}/students")
    public ResponseEntity<List<StudentTestGradeDto>> getStudentsForTest(@PathVariable Long testId) {
        return new ResponseEntity<>(subjectStudentTestService.findStudentTestGradeDtos(testId), HttpStatus.OK);
    }

    @CrossOrigin(origins = "*")
    @GetMapping("/tests")
    public ResponseEntity<List<StudentTestSimpleDto>> getTestsForProfessor() {
        return new ResponseEntity<>(subjectProfessorService.findTestsForCurrentProfessor(), HttpStatus.OK);
    }

}
