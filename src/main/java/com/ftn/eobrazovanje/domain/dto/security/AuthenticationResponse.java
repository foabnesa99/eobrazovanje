package com.ftn.eobrazovanje.domain.dto.security;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor
@Data
@Builder
public class AuthenticationResponse {

    private String token;

}
