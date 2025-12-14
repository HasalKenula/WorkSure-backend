package com.worksure.worksure.entity;

import java.time.LocalDate;
import java.time.LocalTime;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Hire {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate bookingDate;

    private LocalTime bookingTime;

    private String description;

    @JsonProperty("isBooked")
    private boolean isBooked;

    @JsonProperty("isPending")
    private boolean isPending;

    @JsonProperty("isOngoing")
    private boolean isOngoing;

    @JsonProperty("isComplete")
    private boolean isComplete;

    @ManyToOne
    @JoinColumn(name = "workerId")
    private Worker worker;

    @ManyToOne
    @JoinColumn(name = "userId")
    private User user;
}
