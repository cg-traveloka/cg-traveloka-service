package com.cgtravelokaservice.service;

import com.cgtravelokaservice.dto.HotelRegisterFormDTO;
import com.cgtravelokaservice.entity.hotel.Hotel;
import com.cgtravelokaservice.entity.hotel.HotelHotelUtility;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface IHotelUtilityService {
    List <HotelHotelUtility> createUtilitiesForNewHotel(Hotel hotel, HotelRegisterFormDTO hotelRegisterForm);
}
