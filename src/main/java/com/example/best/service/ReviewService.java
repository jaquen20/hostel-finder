package com.example.best.service;

import com.example.best.model.Hostels;
import com.example.best.model.Review;
import com.example.best.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewService {
    @Autowired
    ReviewRepository reviewRepository;
    public List<Review> getReviewsForProduct(Long id) {
        return reviewRepository.findByHid(id);
    }

    public void saveReview(Review review) {
        reviewRepository.save(review);
    }

    public void saveReviews(Review newreview) {
    }
}
