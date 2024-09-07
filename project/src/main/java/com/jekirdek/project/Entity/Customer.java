package com.jekirdek.project.Entity;

import jakarta.persistence.*;
import lombok.*;


import java.time.LocalDateTime;

@Entity
@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "customer")
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    @Column(name = "email")
    private String email;
    @Column(name = "region")
    private String region;
    @Column(name = "registration_date")
    private LocalDateTime registrationDate;

    @PrePersist
    protected void onCreate() {
        this.registrationDate = LocalDateTime.now();
    }

}
