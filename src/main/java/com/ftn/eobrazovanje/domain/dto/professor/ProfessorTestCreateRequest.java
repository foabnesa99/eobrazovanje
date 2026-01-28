package com.ftn.eobrazovanje.domain.dto.professor;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ProfessorTestCreateRequest {

    Long subjectId;
    String title;
    LocalDateTime dateTime;

}
