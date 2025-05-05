package com.ftn.eobrazovanje.domain.dto.studyProgram;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class StudyProgramDto {
    private Long id;
    private String name;
    private String description;
    private String code;
}
