package com.ftn.eobrazovanje.controller;


import com.ftn.eobrazovanje.domain.dto.security.AuthenticationRequest;
import com.ftn.eobrazovanje.domain.dto.security.AuthenticationResponse;
import com.ftn.eobrazovanje.service.security.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("/authenticate")
    @CrossOrigin(origins = "*")
    public ResponseEntity<AuthenticationResponse> authenticate(
            @RequestBody AuthenticationRequest request
    ) {
        return ResponseEntity.ok(authenticationService.authenticate(request));
    }

    @GetMapping("/test")
    public String test(){
        return "test";
    }

}
