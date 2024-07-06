package com.ftn.eobrazovanje.domain.entity.user;

import lombok.*;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Token {

    @Id
    @GeneratedValue
    private Long id;
    private String token;
    private LocalDateTime expiryDateTime;
    private LocalDateTime createdDateTime;
    private LocalDateTime validatedDateTime;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;


}
