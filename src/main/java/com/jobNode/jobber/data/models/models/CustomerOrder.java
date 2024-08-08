package com.jobNode.jobber.data.models.models;

import com.jobNode.jobber.data.models.enums.Status;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

import static jakarta.persistence.EnumType.STRING;
import static jakarta.persistence.GenerationType.IDENTITY;
import static java.time.LocalDateTime.now;

@Data
@Table
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class CustomerOrder {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;
    private LocalDateTime timeStamp;
    @Enumerated(value = STRING)
    private Status status;
    @ManyToOne
    private Providers provider;
    @ManyToOne
    private User customer;
    private LocalDateTime timeUpdated;
    @PrePersist
    private void setTimeStamp(){
        timeStamp= now();
    }
    @PreUpdate
    private void setTimeUpdated(){
        timeUpdated = now();
    }
}
