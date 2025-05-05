package com.ftn.eobrazovanje.controller;

import com.ftn.eobrazovanje.domain.dto.security.AuthenticationRequest;
import com.ftn.eobrazovanje.domain.dto.security.AuthenticationResponse;
import com.ftn.eobrazovanje.domain.dto.user.UserCreateRequest;
import com.ftn.eobrazovanje.domain.dto.user.UserMapper;
import com.ftn.eobrazovanje.domain.dto.user.UserSimpleDto;
import com.ftn.eobrazovanje.domain.entity.user.User;
import com.ftn.eobrazovanje.service.UserService;
import com.ftn.eobrazovanje.service.security.AuthenticationService;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
@Tag(name = "User Controller", description = "Set of endpoints for user management")
@Slf4j
public class UserController {

    private final UserService userService;
    private final AuthenticationService authenticationService;

    public UserController(UserService userService, AuthenticationService authenticationService) {
        this.userService = userService;
        this.authenticationService = authenticationService;
    }

    @PostMapping("/authenticate")
    @CrossOrigin(origins = "*")
    public ResponseEntity<AuthenticationResponse> authenticate(
            @RequestBody AuthenticationRequest request
    ) {
        return ResponseEntity.ok(authenticationService.authenticate(request));
    }


    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "User successfully created"),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error"),
    })
    @CrossOrigin(origins = "*")
    @GetMapping()
    public ResponseEntity<List<UserCreateRequest>> getUsers() {
        List<UserCreateRequest> users = userService.findAll().stream().map(user -> {
            UserCreateRequest userCreateRequest = new UserCreateRequest();
            userCreateRequest.setEmail(user.getEmail());
            userCreateRequest.setFirstName(user.getFirstName());
            userCreateRequest.setLastName(user.getLastName());
            userCreateRequest.setRole(user.getRole());
            userCreateRequest.setPhone(user.getPhone());
            return userCreateRequest;
        }).toList();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "User successfully created"),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error"),
    })
    @CrossOrigin(origins = "*")
    @GetMapping("/professors")
    public ResponseEntity<List<UserSimpleDto>> getProfessors() {
        List<UserSimpleDto> userSimpleDtos = userService.findAllProfessors().stream().map(u -> new UserSimpleDto(u.getId(), u.getFirstName(), u.getLastName(), u.getEmail())).toList();
        return new ResponseEntity<>(userSimpleDtos, HttpStatus.OK);
    }

    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "User successfully created"),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error"),
    })
    @CrossOrigin(origins = "*")
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserCreateRequest> createUser(@RequestBody @Valid UserCreateRequest userCreateRequest) {
        log.info("User created: {}", userCreateRequest);
        log.info("User successfully created: {}", userCreateRequest);
        return new ResponseEntity<>(new UserCreateRequest(), HttpStatus.CREATED);
    }
}
