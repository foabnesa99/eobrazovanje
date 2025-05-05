package com.ftn.eobrazovanje.controller;

import com.ftn.eobrazovanje.domain.dto.studyProgram.StudyProgramCreateRequest;
import com.ftn.eobrazovanje.domain.dto.studyProgram.StudyProgramDto;
import com.ftn.eobrazovanje.domain.dto.subject.SubjectDto;
import com.ftn.eobrazovanje.domain.dto.user.UserCreateRequest;
import com.ftn.eobrazovanje.service.StudyProgramService;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/study-program")
@Tag(name = "Subject Controller", description = "Set of endpoints for subject management")
@Slf4j
public class StudyProgramController {

    private final StudyProgramService studyProgramService;

    public StudyProgramController(StudyProgramService studyProgramService) {
        this.studyProgramService = studyProgramService;
    }

    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Study program dto list"),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error"),
    })
    @CrossOrigin(origins = "*")
    @GetMapping()
    public ResponseEntity<List<StudyProgramDto>> getUsers() {
        List<StudyProgramDto> studyProgramDtos = studyProgramService.findAll().stream().map(sub ->
                new StudyProgramDto(sub.getId(), sub.getName(), sub.getDescription(), sub.getCode())).toList();
        return new ResponseEntity<>(studyProgramDtos, HttpStatus.OK);
    }

    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Study program successfully created"),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error"),
    })
    @CrossOrigin(origins = "*")
    @PostMapping(value = "/create", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<StudyProgramDto> createStudyProgram(@RequestBody @Valid StudyProgramCreateRequest createRequest) {
        studyProgramService.createStudyProgram(createRequest);
        return new ResponseEntity<>(new StudyProgramDto(), HttpStatus.CREATED);
    }

}
