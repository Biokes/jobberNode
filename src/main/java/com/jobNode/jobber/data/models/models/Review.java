package com.jobNode.jobber.data.models.models;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

import static jakarta.persistence.GenerationType.IDENTITY;
import static java.time.LocalDateTime.now;

@Table(name="reviews")
@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Review {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;
    @ManyToOne
    private User reviewer;
    @ManyToOne
    private Providers provider;
    private String description;
    private LocalDateTime timeCreated;
    @PrePersist
    private void setTimeCreated(){
        timeCreated= now();
    }
}
