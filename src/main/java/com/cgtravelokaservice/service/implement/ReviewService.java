package com.cgtravelokaservice.service.implement;

import com.cgtravelokaservice.dto.request.ReviewRequestDTO;
import com.cgtravelokaservice.entity.booking.RoomContract;
import com.cgtravelokaservice.entity.hotel.Hotel;
import com.cgtravelokaservice.entity.hotel.HotelReview;
import com.cgtravelokaservice.entity.hotel.ReviewImage;
import com.cgtravelokaservice.repo.HotelReviewRepo;
import com.cgtravelokaservice.repo.ReviewImageRepo;
import com.cgtravelokaservice.repo.RoomContractRepo;
import com.cgtravelokaservice.service.IImageService;
import com.cgtravelokaservice.service.IReviewService;
import com.cgtravelokaservice.util.implement.ConvertUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class ReviewService implements IReviewService {
    @Autowired
    private HotelReviewRepo hotelReviewRepo;

    @Autowired
    private RoomContractRepo contractRepo;

    @Autowired
    private ConvertUtil convertUtil;

    @Autowired
    private IImageService imageService;

    @Autowired
    private ReviewImageRepo reviewImageRepo;

    public HotelReview saveReview(ReviewRequestDTO requestDTO) {
        RoomContract contract = contractRepo.findById(requestDTO.getContractId()).orElse(null);
        if (!"booked".equals(contract.getStatus()) && contract.getStartDate().isAfter(LocalDate.now())) {
            throw new RuntimeException("Chưa thể đánh giá phòng này");
        }

        contract.setEnableReview(true);
        contractRepo.saveAndFlush(contract);
        HotelReview review = convertUtil.convertDTOToHotelReview(requestDTO);
        review.setCommentTime(LocalDateTime.now());
        review = hotelReviewRepo.saveAndFlush(review);

        Double averageRatingPoints = calculateAverageRatingPoints(contract.getId());
        Hotel hotel = contract.getRoom().getHotel();
        hotel.setAveragePoint(averageRatingPoints);
        return review;
    }

    public boolean setImagesForReview(HotelReview hotelReview, List<MultipartFile> files) {
        List<ReviewImage> reviews = new ArrayList<>();
        for (MultipartFile file : files) {
            try {
                String imageUrl = imageService.save(file);
                ReviewImage reviewImage = new ReviewImage();
                reviewImage.setUrl(imageUrl);
                reviewImage.setHotelReview(hotelReview);
                reviews.add(reviewImage);
            } catch (Exception e) {
                return false;
            }
        }
        reviewImageRepo.saveAllAndFlush(reviews);
        return true;
    }

    @Override
    public Double calculateAverageRatingPoints(Integer roomContractId) {
        List<HotelReview> reviews = hotelReviewRepo.findByRoomContractId(roomContractId);
        if (reviews.isEmpty()) {
            return null;
        }
        double totalRatingPoints = reviews.stream().mapToDouble(HotelReview::getRatingPoint).sum();
        double averageRatingPoints = totalRatingPoints / reviews.size();
        return (Math.round(averageRatingPoints * 10)) / 10.0;
    }
}
