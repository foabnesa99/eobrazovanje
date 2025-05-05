package com.ftn.eobrazovanje.domain.dto.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserSimpleDto {

    private Long id;
    private String firstName;
    private String lastName;
    private String email;

}
