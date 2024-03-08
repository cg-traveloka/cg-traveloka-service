package com.cgtravelokaservice.controller;

import com.cgtravelokaservice.dto.HotelRegisterForm;
import com.cgtravelokaservice.entity.hotel.Hotel;
import com.cgtravelokaservice.entity.hotel.HotelHotelUtility;
import com.cgtravelokaservice.repo.HotelHotelUtilityRepo;
import com.cgtravelokaservice.repo.HotelRepo;
import com.cgtravelokaservice.service.IHotelService;
import com.cgtravelokaservice.service.IHotelUtilityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
public class HotelController {
    @Autowired
    IHotelService hotelService;
    @Autowired
    HotelRepo hotelRepo;
    @Autowired
    IHotelUtilityService hotelUtilityService;
    @Autowired
    HotelHotelUtilityRepo hotelHotelUtilityRepo;

    @PostMapping(value = "/api/hotels", consumes = "multipart/form-data")
    public ResponseEntity <?> registerHotel(@Validated @ModelAttribute HotelRegisterForm hotelRegisterForm, BindingResult bindingResult) {
//        Tạo data bảng hotel
        Hotel hotel =
                hotelService.convertToNewHotel(hotelRegisterForm);
        hotel = hotelRepo.saveAndFlush(hotel);

//        Tạo data bảng tiện ích - hotel
        List <HotelHotelUtility>
                hotelHotelUtilities =
                hotelUtilityService.createUtilitiesForNewHotel(hotel, hotelRegisterForm);
        hotelHotelUtilityRepo.saveAll(hotelHotelUtilities);

//        Tạo data bảng image - hotel
        List <MultipartFile> images =
                hotelRegisterForm.getImages();
        hotelService.setImagesForHotel(hotel, images);

        return ResponseEntity.ok().body(hotel);
    }

}
