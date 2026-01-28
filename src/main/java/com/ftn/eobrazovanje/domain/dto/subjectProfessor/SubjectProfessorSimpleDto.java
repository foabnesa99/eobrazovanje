package com.ftn.eobrazovanje.domain.dto.subjectProfessor;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SubjectProfessorSimpleDto {

    private Long id;
    private String title;
    private String description;

}
