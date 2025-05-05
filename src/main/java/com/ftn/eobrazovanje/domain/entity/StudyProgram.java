package com.ftn.eobrazovanje.domain.entity;

import com.ftn.eobrazovanje.domain.entity.base.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor

@Entity
@Table
public class StudyProgram extends BaseEntity {

    private String name;

    private String description;

    private String code;
}
