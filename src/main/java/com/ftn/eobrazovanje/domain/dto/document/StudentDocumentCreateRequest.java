package com.ftn.eobrazovanje.domain.dto.document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class StudentDocumentCreateRequest {
    private Long studentId;
    private String title;
    private String documentType;
    private String filePath;
}
