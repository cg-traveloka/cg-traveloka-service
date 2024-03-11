package com.cgtravelokaservice.util.implement;

import com.cgtravelokaservice.dto.HotelRegisterFormDTO;
import com.cgtravelokaservice.entity.hotel.Hotel;
import com.cgtravelokaservice.repo.CityRepo;
import com.cgtravelokaservice.repo.HotelImgRepo;
import com.cgtravelokaservice.service.IImageService;
import com.cgtravelokaservice.util.IConvertUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ConvertUtility implements IConvertUtility {
    @Autowired
    CityRepo cityRepo;
    @Autowired
    IImageService imageService;
    @Autowired
    HotelImgRepo hotelImgRepo;

    public Hotel hotelRegisterFormToHotel(HotelRegisterFormDTO hotelRegisterForm) {
        Hotel hotel = new Hotel();
        hotel.setHotelName(hotelRegisterForm.getHotelName());
        hotel.setDescription(hotelRegisterForm.getDescription());
        hotel.setHotelStar(hotelRegisterForm.getHotelStar());
        hotel.setAddress(hotelRegisterForm.getAddress());
        hotel.setHotelBookedNumbers(0);
        hotel.setCity(cityRepo.getReferenceById(hotelRegisterForm.getCityId()));
        return hotel;
    }
    
}
