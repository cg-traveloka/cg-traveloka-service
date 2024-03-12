package com.cgtravelokaservice.controller;

import com.cgtravelokaservice.dto.HotelRegisterFormDTO;
import com.cgtravelokaservice.dto.request.HotelSearchDTO;
import com.cgtravelokaservice.dto.response.HotelsResponeDTO;
import com.cgtravelokaservice.entity.hotel.Hotel;
import com.cgtravelokaservice.entity.hotel.HotelHotelUtility;
import com.cgtravelokaservice.repo.HotelHotelUtilityRepo;
import com.cgtravelokaservice.repo.HotelRepo;
import com.cgtravelokaservice.service.IHotelService;
import com.cgtravelokaservice.service.IHotelUtilityService;
import com.cgtravelokaservice.util.IConvertUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
    @Autowired
    IConvertUtil convertUtility;

    @PostMapping(value = "/api/hotels", consumes = "multipart/form-data")
    public ResponseEntity <?> registerHotel(@Validated @ModelAttribute HotelRegisterFormDTO hotelRegisterForm, BindingResult bindingResult) {
//        Tạo data bảng hotel
        Hotel hotel =
                convertUtility.hotelRegisterFormToHotel(hotelRegisterForm);
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

    @GetMapping("/api/search/hotels")
    public ResponseEntity <?> search(@RequestBody HotelSearchDTO hotelSearchDTO) {
        HotelsResponeDTO hotelsResponeDTO =
                hotelService.search(hotelSearchDTO);
        return ResponseEntity.ok().body(hotelsResponeDTO);
    }
}
