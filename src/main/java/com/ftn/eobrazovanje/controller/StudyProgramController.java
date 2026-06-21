package com.ftn.eobrazovanje.controller;

import com.ftn.eobrazovanje.domain.dto.studyProgram.StudyProgramCreateRequest;
import com.ftn.eobrazovanje.domain.dto.studyProgram.StudyProgramDto;
import com.ftn.eobrazovanje.service.StudyProgramService;
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
@RequestMapping("/api/study-program")
@Tag(name = "Study Program Controller", description = "Set of endpoints for study program management")
@Slf4j
@AllArgsConstructor
public class StudyProgramController {

    private final StudyProgramService studyProgramService;

    @CrossOrigin(origins = "*")
    @GetMapping()
    public ResponseEntity<List<StudyProgramDto>> getStudyPrograms() {
        return new ResponseEntity<>(studyProgramService.findAllDtos(), HttpStatus.OK);
    }

    @CrossOrigin(origins = "*")
    @PostMapping(value = "/create", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> createStudyProgram(@RequestBody @Valid StudyProgramCreateRequest createRequest) {
        studyProgramService.createStudyProgram(createRequest);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

}
