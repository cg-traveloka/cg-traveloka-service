package com.cgtravelokaservice.controller;

import com.cgtravelokaservice.entity.hotel.HotelHotelUtility;
import com.cgtravelokaservice.repo.HotelHotelUtilityRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class HotelUilityController {
    @Autowired
    HotelHotelUtilityRepo hotelHotelUtilityRepo;

    @GetMapping("/api/utilities")
    public ResponseEntity <?> getHotelUtility(@ModelAttribute("hotelId") Integer hotelId) {
        List <HotelHotelUtility> utilities =
                hotelHotelUtilityRepo.findAllByHotelId(hotelId);
        return ResponseEntity.ok().body(utilities);
    }
}
