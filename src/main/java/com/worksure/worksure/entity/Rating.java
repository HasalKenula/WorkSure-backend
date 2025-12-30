package com.worksure.worksure.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Rating {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int stars; // 1-5 stars

    // Many ratings can belong to one worker
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "worker_id") // foreign key in DB
    private Worker worker;
}
