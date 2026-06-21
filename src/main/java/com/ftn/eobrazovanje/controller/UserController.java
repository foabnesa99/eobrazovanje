package com.ftn.eobrazovanje.controller;

import com.ftn.eobrazovanje.domain.dto.professor.ProfessorDto;
import com.ftn.eobrazovanje.domain.dto.security.AuthenticationRequest;
import com.ftn.eobrazovanje.domain.dto.security.AuthenticationResponse;
import com.ftn.eobrazovanje.domain.dto.student.StudentDto;
import com.ftn.eobrazovanje.domain.dto.user.UserDto;
import com.ftn.eobrazovanje.service.ProfessorService;
import com.ftn.eobrazovanje.service.StudentService;
import com.ftn.eobrazovanje.service.UserService;
import com.ftn.eobrazovanje.service.security.AuthenticationService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
@Tag(name = "User Controller", description = "Set of endpoints for user management")
@Slf4j
@AllArgsConstructor
public class UserController {

    private final UserService userService;
    private final StudentService studentService;
    private final ProfessorService professorService;
    private final AuthenticationService authenticationService;

    @PostMapping("/authenticate")
    @CrossOrigin(origins = "*")
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request) {
        return ResponseEntity.ok(authenticationService.authenticate(request));
    }

    @CrossOrigin(origins = "*")
    @GetMapping()
    public ResponseEntity<List<UserDto>> getUsers() {
        return new ResponseEntity<>(userService.findAllDtos(), HttpStatus.OK);
    }

    @CrossOrigin(origins = "*")
    @GetMapping("/professors")
    public ResponseEntity<List<ProfessorDto>> getProfessors() {
        return new ResponseEntity<>(professorService.findAllDtos(), HttpStatus.OK);
    }

    @CrossOrigin(origins = "*")
    @GetMapping("/students")
    public ResponseEntity<List<StudentDto>> getStudents() {
        return new ResponseEntity<>(studentService.findAllDtos(), HttpStatus.OK);
    }
}
