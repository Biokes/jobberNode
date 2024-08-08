package com.jobNode.jobber.data.models.models;

import com.jobNode.jobber.data.models.enums.Services;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

import static jakarta.persistence.CascadeType.PERSIST;
import static jakarta.persistence.EnumType.STRING;
import static jakarta.persistence.GenerationType.IDENTITY;

@Data
@Table
@Entity
public class Providers {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;
    @OneToOne(cascade = PERSIST)
    private User user;
    @Enumerated(value = STRING)
    private List<Services> services;
}
