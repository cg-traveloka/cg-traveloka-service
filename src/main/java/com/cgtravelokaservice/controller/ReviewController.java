package com.cgtravelokaservice.controller;

import com.cgtravelokaservice.dto.request.ReviewRequestDTO;
import com.cgtravelokaservice.entity.hotel.HotelReview;
import com.cgtravelokaservice.service.implement.CustomerService;
import com.cgtravelokaservice.service.implement.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
public class ReviewController {
    @Autowired
    private ReviewService reviewService;

    @PostMapping("/api/review")
    public ResponseEntity<?> createReview(@ModelAttribute ReviewRequestDTO requestDTO) {
        try {
            HotelReview review = reviewService.saveReview(requestDTO);
            List<MultipartFile> images = requestDTO.getImages();
            reviewService.setImagesForReview(review, images);
            return new ResponseEntity<>(review, HttpStatus.CREATED);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("Không thể đánh giá review", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

//    @GetMapping("/api/hotel/{hotelId}/averageRating")
//    public ResponseEntity<?> getAverageRating(@PathVariable Integer hotelId) {
//        Double averageRating = reviewService.calculateAverageRatingPoints(hotelId);
//        if (averageRating == null) {
//            return ResponseEntity.notFound().build();
//        }
//        return ResponseEntity.ok(averageRating);
//    }
}
