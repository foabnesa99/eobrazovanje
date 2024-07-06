package com.ftn.eobrazovanje.domain.entity.user;

import com.ftn.eobrazovanje.domain.common.ProfessorRole;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.springframework.stereotype.Indexed;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor

@Indexed
@Entity(name = "professor")
@Table(name = "`professor`", uniqueConstraints = @UniqueConstraint(name = "email_idx", columnNames = {"email"}))
@SQLDelete(sql = "UPDATE professor SET deleted = true WHERE id = ?")
public class Professor extends User {

    private ProfessorRole professorRole;


}
