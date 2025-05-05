package com.ftn.eobrazovanje.domain.dto.subject;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class SubjectCreateRequest {

    private String title;
    private String description;

}
