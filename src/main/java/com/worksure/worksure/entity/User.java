package com.worksure.worksure.entity;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(unique = true)
    private String username;

    private String email;

    private String contact;

    private String address;

    private String password;

    private String imageUrl;

    @JsonIgnore
    @OneToMany(mappedBy = "user")
    private List<Contact> contacts;

    @JsonIgnore
    @OneToOne(mappedBy = "user")
    private Worker workers;

    @JsonIgnore
    @OneToMany(mappedBy = "user")
    private List<Hire> hires;

    
    @JsonIgnore
    @OneToOne(mappedBy = "user")
    private Payment payment;
    
}
