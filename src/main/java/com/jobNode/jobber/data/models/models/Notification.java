package com.jobNode.jobber.data.models.models;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

import static jakarta.persistence.GenerationType.IDENTITY;
import static java.time.LocalDateTime.now;

@Setter
@Getter
@Table
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Notification {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;
    @ManyToOne
    private User user;
    @Setter(AccessLevel.NONE)
    private LocalDateTime timeStamp;
    private String description;
    private boolean viewed;
    @PrePersist
    private void setTimeStamp(){
        timeStamp=now();
    }
}
