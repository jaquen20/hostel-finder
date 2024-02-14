package com.example.best.dto;

import com.example.best.model.Review;
import lombok.Data;

import java.util.List;

@Data
public class HostelDto {
    private Long id;
    private String name;
    private String city;
    private String address;
    private String mobileNo;
    private String type;
    private String rent;
    private String description;
    private String profileImage;

    // Other fields as needed

    // Constructors, getters, setters, etc.
}

