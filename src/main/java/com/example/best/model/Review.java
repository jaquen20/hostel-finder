package com.example.best.model;

import jakarta.persistence.*;
import lombok.Data;


@Entity
@Data
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

//    @ManyToOne
//    @JoinColumn(name = "user_id")
//    private Users user;

//    @ManyToOne
//    @JoinColumn(name = "hostel_id")
//    private Hostels hostels;

    private int rating;

    @Column(columnDefinition = "TEXT")
    private String comment;

    private Long hid;




}

