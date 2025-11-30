package com.worksure.worksure.entity;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Worker {
    @Id
    private String id;  // Custom ID like W0001

    private String fullName;

    @Column(unique = true)
    private String email;

    private String phoneNumber;

    // Availability
    private boolean mon;
    private boolean tue;
    private boolean wed;
    private boolean thu;
    private boolean fri;
    private boolean sat;
    private boolean sun;

    private LocalTime preferredStartTime;
    private LocalTime preferredEndTime;

    private String preferredServiceLocation;

    @OneToMany(mappedBy = "worker", cascade = CascadeType.ALL, orphanRemoval = true)
      @JsonManagedReference
    private List<Certificate> certificates = new ArrayList<>();

    @OneToMany(mappedBy = "worker", cascade = CascadeType.ALL, orphanRemoval = true)
      @JsonManagedReference
    private List<JobExperience> jobExperiences = new ArrayList<>();
}
