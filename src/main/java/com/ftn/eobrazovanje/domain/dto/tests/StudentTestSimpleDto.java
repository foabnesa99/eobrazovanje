package com.ftn.eobrazovanje.domain.dto.tests;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class StudentTestSimpleDto {

    Long id;
    String title;
    LocalDateTime dateTime;

}
