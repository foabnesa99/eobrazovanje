package com.ftn.eobrazovanje.domain.entity.relational;

import com.ftn.eobrazovanje.domain.entity.Subject;
import com.ftn.eobrazovanje.domain.entity.base.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.*;

import java.time.LocalDateTime;


@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor

@Entity
@Table
public class SubjectTest extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "subject_id")
    private Subject subject;

    private String title;

    private LocalDateTime dateTime;

    private boolean cancelled;


}
