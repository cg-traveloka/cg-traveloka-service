package com.cgtravelokaservice.controller;

import com.cgtravelokaservice.entity.hotel.HotelImg;
import com.cgtravelokaservice.repo.HotelImgRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class HotelImageController {
    @Autowired
    HotelImgRepo hotelImgRepo;

    @GetMapping(value = "/api/images")
    public ResponseEntity <?> getImage(@ModelAttribute("hotelId") Integer hotelId) {
        List <HotelImg> images =
                hotelImgRepo.findAllByHotelId(hotelId);
        return ResponseEntity.ok().body(images);
    }
}
