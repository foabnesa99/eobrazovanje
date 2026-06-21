package com.ftn.eobrazovanje.domain.dto.document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class StudentDocumentDto {
    private Long id;
    private String title;
    private String documentType;
    private String filePath;
}
