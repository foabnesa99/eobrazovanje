package com.ftn.eobrazovanje.domain.entity.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ftn.eobrazovanje.domain.common.UserRole;
import com.ftn.eobrazovanje.domain.common.UserStatus;
import com.ftn.eobrazovanje.domain.entity.base.BaseEntity;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import jakarta.persistence.*;
import org.springframework.stereotype.Indexed;

import java.time.LocalDate;
import java.util.*;

import static jakarta.persistence.EnumType.STRING;


@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor

@Table
@Entity
@Indexed
@SQLDelete(sql = "UPDATE user SET deleted = true WHERE id = ?")
public class User extends BaseEntity{

    @NotNull
    @Email
    private String email;

    @JsonIgnore
    private String password;

    @Builder.Default
    @Column(columnDefinition = "BOOLEAN NOT NULL DEFAULT FALSE", nullable = false)
    private boolean active = false;

    private String firstName;

    private String lastName;

    private String phone;

    @JsonIgnore
    private String emailVerificationKey;

    @Enumerated(STRING)
    @Builder.Default
    private UserStatus status = UserStatus.ACTIVE;

    @JsonIgnore
    private Long passwordRecoveryTime;

    @Enumerated(STRING)
    @Builder.Default
    private UserRole role = UserRole.STUDENT;

    @Transient
    private String accessToken;

    private LocalDate registrationDate;


    public List<GrantedAuthority> getAuthorities() {
        if (role == null) {
            return Collections.singletonList(new SimpleGrantedAuthority("UNCONFIRMED"));
        }
        return Collections.singletonList(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", User.class.getSimpleName() + "[", "]")
                .add("id='" + getId() + "'")
                .add("firstName='" + getFirstName() + "'")
                .add("lastName='" + getLastName() + "'")
                .add("email='" + getEmail() + "'")
                .toString();
    }

    public boolean hasRole(UserRole role) {
        if (Objects.nonNull(role))
            return role==this.role;
        return false;
    }

    @Transient
    public UserStatus getStatus() {
        if (status == UserStatus.INVITATION_PENDING) {
            if (passwordRecoveryTime != null && System.currentTimeMillis() > passwordRecoveryTime) {
                status = UserStatus.INVITATION_EXPIRED;
            }
        }
        return status;
    }

    @Transient
    public String getFullName() {
        return firstName + " " + lastName;
    }


}
