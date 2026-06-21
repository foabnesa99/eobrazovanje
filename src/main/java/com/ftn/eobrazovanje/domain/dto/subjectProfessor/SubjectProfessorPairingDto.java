package com.ftn.eobrazovanje.domain.dto.subjectProfessor;

import com.ftn.eobrazovanje.domain.common.ProfessorRole;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class SubjectProfessorPairingDto {

    private Long subjectId;
    private Long professorId;
    private String subjectTitle;
    private String professorName;
    private ProfessorRole professorRole;

}
