package com.example.best.repository;

import com.example.best.model.Hostels;
import com.example.best.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
//    List<Review> findByHostels(Hostels room);

    List<Review> findByHid(Long id);
}

