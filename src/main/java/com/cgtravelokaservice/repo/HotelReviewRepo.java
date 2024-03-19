package com.cgtravelokaservice.repo;

import com.cgtravelokaservice.entity.hotel.HotelReview;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HotelReviewRepo extends JpaRepository<HotelReview, Integer> {
    List<HotelReview> findByRoomContractId(Integer roomContractId);
}
