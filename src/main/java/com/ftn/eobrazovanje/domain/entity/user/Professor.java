package com.ftn.eobrazovanje.domain.entity.user;

import com.ftn.eobrazovanje.domain.common.ProfessorRole;
import com.ftn.eobrazovanje.domain.entity.base.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.stereotype.Indexed;

@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor

@Indexed
@Entity(name = "professor")
@Table
public class Professor extends BaseEntity {

    @OneToOne(fetch = FetchType.EAGER, cascade = jakarta.persistence.CascadeType.ALL)
    private User user;

    private ProfessorRole professorRole;


}
