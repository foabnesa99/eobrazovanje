package com.ftn.eobrazovanje.domain.entity.base;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import org.hibernate.annotations.Type;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.*;

@EntityListeners(AuditingEntityListener.class)
@MappedSuperclass
@Getter
public abstract class BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Version
    @JsonIgnore
    private Long version;

    @JsonIgnore
    @javax.persistence.Column(columnDefinition = "BOOLEAN NOT NULL DEFAULT FALSE", nullable = false)
    private boolean deleted = false;
}
