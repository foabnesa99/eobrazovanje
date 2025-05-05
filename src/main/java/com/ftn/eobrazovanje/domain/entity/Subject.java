package com.ftn.eobrazovanje.domain.entity;

import com.ftn.eobrazovanje.domain.entity.base.BaseEntity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.springframework.stereotype.Indexed;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

@Indexed

@Table
@Entity
@SQLDelete(sql = "UPDATE subject SET deleted = true WHERE id = ? and `version` = ?")
public class Subject extends BaseEntity {

    private String title;

    private String description;

    public Subject(String title, String description) {
        this.title = title;
        this.description = description;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    private StudyProgram studyProgram;

}
