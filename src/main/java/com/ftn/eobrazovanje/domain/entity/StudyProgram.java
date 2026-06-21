package com.ftn.eobrazovanje.domain.entity;

import com.ftn.eobrazovanje.domain.entity.base.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor

@Entity
@Table
@SQLDelete(sql = "UPDATE study_program SET deleted = true WHERE id = ? and `version` = ?")
@SQLRestriction("deleted = false")
public class StudyProgram extends BaseEntity {

    private String name;

    private String description;

    private String code;
}
