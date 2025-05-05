package com.ftn.eobrazovanje.controller;

import com.ftn.eobrazovanje.domain.dto.subject.SubjectDto;
import com.ftn.eobrazovanje.domain.dto.user.UserCreateRequest;
import com.ftn.eobrazovanje.service.SubjectService;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/subject")
@Tag(name = "Subject Controller", description = "Set of endpoints for subject management")
@Slf4j
public class SubjectController {

    private final SubjectService subjectService;


    public SubjectController(SubjectService subjectService) {
        this.subjectService = subjectService;
    }


    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "User successfully created"),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error"),
    })
    @CrossOrigin(origins = "*")
    @GetMapping()
    public ResponseEntity<List<SubjectDto>> getSubjects() {
        List<SubjectDto> subjectDtoList = subjectService.getAll().stream().map(sub -> new SubjectDto(sub.getId(), sub.getTitle(), sub.getDescription())).toList();
        return new ResponseEntity<>(subjectDtoList, HttpStatus.OK);
    }

}
