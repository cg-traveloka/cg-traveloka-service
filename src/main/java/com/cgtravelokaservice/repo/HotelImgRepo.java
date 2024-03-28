package com.cgtravelokaservice.repo;

import com.cgtravelokaservice.entity.hotel.HotelImg;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HotelImgRepo extends JpaRepository <HotelImg, Integer> {
    List <HotelImg> findAllByHotelId(Integer hotelId);
}
