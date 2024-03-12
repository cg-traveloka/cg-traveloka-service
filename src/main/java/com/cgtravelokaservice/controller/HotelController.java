package com.cgtravelokaservice.controller;

import com.cgtravelokaservice.dto.HotelRegisterFormDTO;
import com.cgtravelokaservice.dto.response.HotelResponseDTO;
import com.cgtravelokaservice.dto.response.ListHotelResponseAndPageNumberDTO;
import com.cgtravelokaservice.entity.hotel.Hotel;
import com.cgtravelokaservice.entity.hotel.HotelHotelUtility;
import com.cgtravelokaservice.repo.HotelHotelUtilityRepo;
import com.cgtravelokaservice.repo.HotelRepo;
import com.cgtravelokaservice.service.IHotelService;
import com.cgtravelokaservice.service.IHotelUtilityService;
import com.cgtravelokaservice.service.implement.BookingService;
import com.cgtravelokaservice.util.IConvertUtil;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@RestController
public class HotelController {
    @Autowired
    private IHotelService hotelService;
    @Autowired
    private HotelRepo hotelRepo;
    @Autowired
    private IHotelUtilityService hotelUtilityService;
    @Autowired
    private HotelHotelUtilityRepo hotelHotelUtilityRepo;
    @Autowired
    private IConvertUtil convertUtility;
    @Autowired
    private BookingService bookingService;

    private final ModelMapper modelMapper = new ModelMapper();

    @PostMapping(value = "/api/hotels", consumes = "multipart/form-data")
    public ResponseEntity<?> registerHotel(@Validated @ModelAttribute HotelRegisterFormDTO hotelRegisterForm, BindingResult bindingResult) {
//        Tạo data bảng hotel
        Hotel hotel = convertUtility.hotelRegisterFormToHotel(hotelRegisterForm);
        hotel = hotelRepo.saveAndFlush(hotel);
        hotel.setHotelBookedNumbers(0);

//        Tạo data bảng tiện ích - hotel
        List<HotelHotelUtility> hotelHotelUtilities = hotelUtilityService.createUtilitiesForNewHotel(hotel, hotelRegisterForm);
        hotelHotelUtilityRepo.saveAll(hotelHotelUtilities);

//        Tạo data bảng image - hotel
        List<MultipartFile> images = hotelRegisterForm.getImages();
        hotelService.setImagesForHotel(hotel, images);

        return ResponseEntity.ok().body(hotel);
    }

    @GetMapping("/hotels")
    public ListHotelResponseAndPageNumberDTO getHotels(Pageable pageable) {
        Slice<Hotel> hotelPage = hotelService.getHotels(pageable);
        List<HotelResponseDTO> hotelResponseDTOList = convertToDTO(hotelPage.getContent());
        return new ListHotelResponseAndPageNumberDTO(hotelResponseDTOList, hotelPage.getNumber());
    }

//    Cách bình thuường

//    private List<HotelResponseDTO> convertToDTO(List<Hotel> hotels) {
//        return hotels.stream().map(hotel -> {
//            HotelResponseDTO hotelResponseDTO = new HotelResponseDTO();
//            hotelResponseDTO.setId(hotel.getId());
//            hotelResponseDTO.setHotelName(hotel.getHotelName());
//            hotelResponseDTO.setHotelStar(hotel.getHotelStar());
//            hotelResponseDTO.setAddress(hotel.getAddress());
//            hotelResponseDTO.setHotelBookedNumbers(hotel.getHotelBookedNumbers());
//            hotelResponseDTO.setAverageRatingPoints(hotelService.calculateAverageRatingPoints(hotel.getId()));
//            return hotelResponseDTO;
//        }).collect(Collectors.toList());
//    }

    //    Dùng ModelMapper để convert từ entity sang DTO
    private List<HotelResponseDTO> convertToDTO(List<Hotel> hotels) {
        return hotels.stream()
                .map(hotel -> modelMapper.map(hotel, HotelResponseDTO.class))
                .collect(Collectors.toList());
    }

    @PostMapping("/hotels/{hotelId}/book")
    public ResponseEntity<String> makeBooking(@PathVariable Integer hotelId) {
        try {
            bookingService.makeBooking(hotelId);
            return ResponseEntity.ok("Booking has been made successfully");
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Hotel with id " + hotelId + " not found");
        }
    }

    @GetMapping("/hotels/sorted")
    public Slice<Hotel> getHotelsSortedByBookedNumbers(Pageable pageable) {
        return hotelService.getHotelsSortedByHotelBookedNumbers(pageable);
    }

    @GetMapping("/hotels/{hotelId}/average-rating")
    public Double calculateAverageRatingPoints(@PathVariable Integer hotelId) {
        return hotelService.calculateAverageRatingPoints(hotelId);
    }
}
