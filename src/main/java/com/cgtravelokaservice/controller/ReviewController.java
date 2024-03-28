package com.cgtravelokaservice.controller;

import com.cgtravelokaservice.dto.request.ReviewRequestDTO;
import com.cgtravelokaservice.entity.booking.RoomContract;
import com.cgtravelokaservice.entity.hotel.HotelReview;
import com.cgtravelokaservice.entity.user.Customer;
import com.cgtravelokaservice.repo.CustomerRepo;
import com.cgtravelokaservice.repo.RoomContractRepo;
import com.cgtravelokaservice.repo.UserRepo;
import com.cgtravelokaservice.service.implement.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
public class ReviewController {
    @Autowired
    private ReviewService reviewService;
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private CustomerRepo customerRepo;
    @Autowired
    private RoomContractRepo roomContractRepo;

    @PostMapping("/api/review")
    public ResponseEntity<?> createReview(@AuthenticationPrincipal UserDetails userDetails, @ModelAttribute ReviewRequestDTO requestDTO) {
        try {
            String username = userDetails.getUsername();
            Customer customer = customerRepo.findByUser(userRepo.findByUsername(username).orElse(null));
            List<RoomContract> roomContracts = roomContractRepo.findAllByCustomerIdAndStatus(customer.getId(), "booked");
            if (roomContracts.isEmpty()) {
                return new ResponseEntity<>("Không có phòng nào đã được book cho khách hàng", HttpStatus.BAD_REQUEST);
            }
            requestDTO.setContractId(roomContracts.getFirst().getId());
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
    @GetMapping("/api/hotel/{hotelId}/averageRating")
    public ResponseEntity<?> getAverageRating(@PathVariable Integer hotelId) {
        Double averageRating = reviewService.calculateAverageRatingPoints(hotelId);
        if (averageRating == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(averageRating);
    }
}
