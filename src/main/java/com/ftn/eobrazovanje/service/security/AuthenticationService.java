package com.ftn.eobrazovanje.service.security;

import com.ftn.eobrazovanje.domain.dto.security.AuthenticationRequest;
import com.ftn.eobrazovanje.domain.dto.security.AuthenticationResponse;
import com.ftn.eobrazovanje.domain.dto.security.CustomUserDetails;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
@AllArgsConstructor
@Transactional
public class AuthenticationService {

    private final UserDetailServiceImpl userDetailService;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        var claims = new HashMap<String, Object>();
        CustomUserDetails user = ((CustomUserDetails) auth.getPrincipal());
        claims.put("fullName", user.getName());
        UserDetails userDetails = userDetailService.loadUserByUsername(user.getUsername());
        var jwtToken = jwtService.generateToken(claims, userDetails);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }
}
