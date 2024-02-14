package com.example.best.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data @Entity
public class Roles {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    @ManyToMany(mappedBy="roles")
    private List<Users> users;
    public Roles() {
    }

}
