package com.cgtravelokaservice.service;

import com.cgtravelokaservice.entity.hotel.Hotel;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public interface IHotelService {
//    Hotel convertToNewHotel(HotelRegisterForm hotelRegisterForm);

    boolean setImagesForHotel(Hotel hotel, List <MultipartFile> files);
}
