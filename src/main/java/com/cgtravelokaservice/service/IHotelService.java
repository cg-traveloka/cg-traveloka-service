package com.cgtravelokaservice.service;

import com.cgtravelokaservice.dto.request.HotelSearchDTO;
import com.cgtravelokaservice.dto.response.HotelsResponseDTO;
import com.cgtravelokaservice.entity.hotel.Hotel;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public interface IHotelService {
    boolean setImagesForHotel(Hotel hotel, List<MultipartFile> files);

    Slice<Hotel> getHotelsSortedByHotelBookedNumbers(Pageable pageable);

    Slice<Hotel> getHotels(Pageable pageable);

    HotelsResponseDTO search(HotelSearchDTO hotelSearchDTO);
}
