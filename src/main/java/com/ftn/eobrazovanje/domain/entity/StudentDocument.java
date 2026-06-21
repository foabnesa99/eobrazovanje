package com.ftn.eobrazovanje.domain.entity;

import com.ftn.eobrazovanje.domain.entity.base.BaseEntity;
import com.ftn.eobrazovanje.domain.entity.user.Student;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor

@Entity
@Table
public class StudentDocument extends BaseEntity {

    private String title;

    private String documentType;

    private String filePath;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id")
    private Student student;
}
