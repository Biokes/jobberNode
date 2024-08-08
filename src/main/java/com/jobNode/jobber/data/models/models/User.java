package com.jobNode.jobber.data.models.models;

import com.jobNode.jobber.data.models.enums.RegisterationState;
import com.jobNode.jobber.data.models.enums.Role;
import com.jobNode.jobber.data.models.models.Address;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

import static jakarta.persistence.CascadeType.PERSIST;
import static jakarta.persistence.EnumType.STRING;
import static jakarta.persistence.GenerationType.IDENTITY;
import static java.time.LocalDateTime.now;

@Table(name="users_table")
@Entity
@Setter
@Getter
@ToString
public class User {
    @Id
    @GeneratedValue(strategy =IDENTITY)
    private Long id;
    private String username;
    private String password;
    @Column(unique = true)
    private String email;
    @OneToOne(cascade = PERSIST)
    private Address address;
    @Enumerated(STRING)
    private Role role;
    @Enumerated(STRING)
    private RegisterationState registerationState;
    @Setter(AccessLevel.NONE)
    private LocalDateTime timeStamp;
    @PrePersist
    private void setTimeSaved(){
        timeStamp=now();
    }
}
