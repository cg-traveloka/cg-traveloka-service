package com.cgtravelokaservice.controller;

import com.cgtravelokaservice.repo.HotelUtilityRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HotelUtilityController {
    @Autowired
    private HotelUtilityRepo hotelUtilityRepo;

    @GetMapping("/api/hotel-utilities/{hotelUtilTypeId}")
    public ResponseEntity<?> findByHotelUtilTypeId(@PathVariable Integer hotelUtilTypeId) {
        return ResponseEntity.ok().body(hotelUtilityRepo.findAllByHotelUtilityType_Id(hotelUtilTypeId,
                Sort.by("name")));

    }
}
