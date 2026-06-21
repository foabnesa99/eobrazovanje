package com.ftn.eobrazovanje.domain.entity.user;

import com.ftn.eobrazovanje.domain.common.ProfessorRole;
import com.ftn.eobrazovanje.domain.entity.base.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;
import org.springframework.stereotype.Indexed;

@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor

@Indexed
@Entity(name = "professor")
@Table
@SQLDelete(sql = "UPDATE professor SET deleted = true WHERE id = ? and `version` = ?")
@SQLRestriction("deleted = false")
public class Professor extends BaseEntity {

    @OneToOne(fetch = FetchType.EAGER, cascade = jakarta.persistence.CascadeType.ALL)
    private User user;

}
