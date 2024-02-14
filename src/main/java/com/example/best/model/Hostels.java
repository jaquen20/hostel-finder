package com.example.best.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Entity @Data
public class Hostels {

    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    private Long id;

    private String name;
    private String city;
    private String address;
    private String mobileNo;
    private String type;
    private String rent;
    private String description;
    private String profileImage;

    @ManyToOne
    @JoinColumn(name="userid")
    @ToString.Exclude
    private Users user;

//    @OneToMany(mappedBy = "hostels")
//    private List<Review> reviews = new ArrayList<>();


//    public void addReview(Review review) {
//        reviews.add(review);
//        review.setHostels(this);
//    }
}
