package com.cgtravelokaservice.service.implement;

import com.cgtravelokaservice.entity.hotel.Hotel;
import com.cgtravelokaservice.repo.HotelRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BookingService {
    @Autowired
    private HotelRepo hotelRepo;

    public void makeBooking(Integer hotelId) {
//            Truy xuất entity Hotel ra khỏi DB
        Hotel hotel = hotelRepo.findById(hotelId).orElseThrow(() -> new RuntimeException("Hotel not found"));

//    Tăng hotelBookedNumbers lên 1
        hotel.setHotelBookedNumbers(hotel.getHotelBookedNumbers() + 1);

//    Lưu lại vào DB
        hotelRepo.save(hotel);
    }
}
