package com.cgtravelokaservice.controller;

import com.cgtravelokaservice.repo.HotelUtilityTypeRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HotelUtilityTypeController {
    @Autowired
    private HotelUtilityTypeRepo hotelUtilityTypeRepo;

    @GetMapping("/api/hotel-utility-types")
    public ResponseEntity<?> findAll() {
        return ResponseEntity.ok().body(hotelUtilityTypeRepo.findAll());
    }
}
