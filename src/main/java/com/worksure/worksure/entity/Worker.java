package com.worksure.worksure.entity;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.worksure.worksure.dto.JobRole;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Worker {
  @Id
  private String id;
  private String fullName;

  @Column(unique = true)
  private String email;

  private String phoneNumber;
  private String nic;

  private String address;

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
  private String pdfUrl;
  @JsonProperty("isBlocked")
  private boolean isBlocked;

  @OneToMany(mappedBy = "worker", cascade = CascadeType.ALL, orphanRemoval = true)
  @JsonManagedReference
  private List<Certificate> certificates = new ArrayList<>();

  @OneToMany(mappedBy = "worker", cascade = CascadeType.ALL, orphanRemoval = true)
  @JsonManagedReference
  private List<JobExperience> jobExperiences = new ArrayList<>();

  @OneToOne
  @JoinColumn(name = "userId")
  private User user;

  @Enumerated(EnumType.STRING)
  private JobRole jobRole;

  @JsonIgnore
  @OneToMany(mappedBy = "worker")
  private List<Hire> hire;

  @JsonIgnore
  @OneToMany(mappedBy = "worker")
  private List<BankDetails> bankDetails;

  @JsonIgnore
  @OneToMany(mappedBy = "worker")
  private List<Transfer> transfers;

}
