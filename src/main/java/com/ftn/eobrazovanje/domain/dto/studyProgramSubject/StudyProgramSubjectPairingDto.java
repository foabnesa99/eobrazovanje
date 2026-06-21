package com.ftn.eobrazovanje.domain.dto.studyProgramSubject;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class StudyProgramSubjectPairingDto {

    private Long subjectId;
    private String subjectTitle;
    private String studyProgramCode;
    private String studyProgramName;

}
