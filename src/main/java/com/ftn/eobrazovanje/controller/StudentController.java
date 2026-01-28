package com.ftn.eobrazovanje.controller;

import com.ftn.eobrazovanje.domain.dto.studyProgram.StudyProgramCreateRequest;
import com.ftn.eobrazovanje.domain.dto.studyProgram.StudyProgramDto;
import com.ftn.eobrazovanje.domain.dto.subject.SubjectDto;
import com.ftn.eobrazovanje.domain.dto.tests.StudentTestSimpleDto;
import com.ftn.eobrazovanje.domain.entity.StudyProgram;
import com.ftn.eobrazovanje.domain.entity.relational.StudyProgramStudent;
import com.ftn.eobrazovanje.domain.entity.user.Student;
import com.ftn.eobrazovanje.domain.entity.user.User;
import com.ftn.eobrazovanje.service.*;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/student")
@Tag(name = "Subject Controller", description = "Set of endpoints for subject management")
@Slf4j
@AllArgsConstructor
public class StudentController {

    private final SubjectService subjectService;
    private final StudentService studentService;
    private final UserService userService;
    private final SubjectTestService subjectTestService;
    private final StudyProgramService studyProgramService;
    private final StudyProgramStudentService studyProgramStudentService;
    private final SubjectStudentTestService subjectStudentTestService;

    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "User successfully created"),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error"),
    })
    @CrossOrigin(origins = "*")
    @GetMapping("/tests")
    public ResponseEntity<List<StudentTestSimpleDto>> getTestsForStudent() {
        User user = userService.fetchCurrentUser();
        Student student = studentService.findByEmail(user.getEmail());
        List<StudentTestSimpleDto> studentTestSimpleDtos = subjectTestService.getAvailableTestsForStudent(student);
        return new ResponseEntity<>(studentTestSimpleDtos, HttpStatus.OK);
    }

    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Study program successfully created"),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error"),
    })
    @CrossOrigin(origins = "*")
    @PutMapping(value = "/test/assign")
    public ResponseEntity<HttpStatus> assignStudentToTest(@RequestParam Long testId) {
        User user = userService.fetchCurrentUser();
        Student student = studentService.findByEmail(user.getEmail());
        subjectStudentTestService.assignStudentToTest(testId, student);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }


}
