package com.cgtravelokaservice.repo;

import com.cgtravelokaservice.entity.hotel.HotelHotelUtility;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HotelHotelUtilityRepo extends JpaRepository <HotelHotelUtility, Integer> {
    List <HotelHotelUtility> findAllByHotelId(Integer hotelId);
}
