package com.example.best.controller;

import com.example.best.dto.HostelDto;
import com.example.best.exception.HostelsNotFoundException;
import com.example.best.exception.UserNotFoundException;
import com.example.best.model.Hostels;
import com.example.best.model.Review;
import com.example.best.model.Users;
import com.example.best.service.HostelService;
import com.example.best.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Optional;
@Controller
public class UserController {
    @Autowired
    HostelService roomService;
    @Autowired
    ReviewService reviewService;

    @GetMapping("/user/UserHome")
    public String UserHome(Model model){
        model.addAttribute("data",roomService.getData());
        return "userHome";
    }




    @GetMapping("/user/productDetails/{id}")
    public String productDetails(@PathVariable Long id,Model model){
        Optional<Hostels> details = roomService.getDataById(id);

        if (details.isPresent()) {
            HostelDto hostelDto = roomService.convertToDto(details.get());
           // Hostels roomDetails=details.get();
            List<Review> reviews=reviewService.getReviewsForProduct(id);

            model.addAttribute("details", hostelDto);

            if (reviews!=null){
                model.addAttribute("reviews",reviews);
            }else {
                //model.addAttribute("","no reviews");
                System.out.println("no reviews yet");
            }



            model.addAttribute("review", new Review());
        }

        return "ProductDetails";
    }


    @PostMapping("/user/addReview/{id}")
    public String addReview(@PathVariable Long id,@ModelAttribute Review review) {
        try {

//            Optional<Hostels> product = roomService.getDataById(id);
//            Hostels details = product.orElseThrow(() -> new HostelsNotFoundException("Hostel with id " + id + " not found"));

            if (review!=null){
                Review Newreview = new Review();
                Newreview.setRating(review.getRating());
                Newreview.setComment(review.getComment());
                Newreview.setHid(id);
              //  Newreview.setHostels(details);
              //  details.addReview(review);

              //  details.setReviews((List<Review>) review);


              //  roomService.saveReviews(details);
                reviewService.saveReview(Newreview);
            }

        }catch (HostelsNotFoundException e){

            return "redirect:/user/UserHome";

        }
        return "redirect:user/UserHome";
    }
}
