package com.cgtravelokaservice.service.implement;

import com.cgtravelokaservice.entity.hotel.Hotel;
import com.cgtravelokaservice.entity.hotel.HotelImg;
import com.cgtravelokaservice.repo.CityRepo;
import com.cgtravelokaservice.repo.HotelImgRepo;
import com.cgtravelokaservice.service.IHotelService;
import com.cgtravelokaservice.service.IImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Service
public class HotelService implements IHotelService {
    @Autowired
    CityRepo cityRepo;
    @Autowired
    IImageService imageService;
    @Autowired
    HotelImgRepo hotelImgRepo;

//    public Hotel convertToNewHotel(HotelRegisterForm hotelRegisterForm) {
//        Hotel hotel = new Hotel();
//        hotel.setHotelName(hotelRegisterForm.getHotelName());
//        hotel.setDescription(hotelRegisterForm.getDescription());
//        hotel.setHotelStar(hotelRegisterForm.getHotelStar());
//        hotel.setAddress(hotelRegisterForm.getAddress());
//        hotel.setHotelBookedNumbers(0);
//        hotel.setCity(cityRepo.getReferenceById(hotelRegisterForm.getCityId()));
//        return hotel;
//    }

    public boolean setImagesForHotel(Hotel hotel, List <MultipartFile> files) {
        List <HotelImg> hotelImgs =
                new ArrayList <>();
        for (MultipartFile file : files) {
            try {
                String imageUrl =
                        imageService.save(file);
                HotelImg hotelImg =
                        new HotelImg();
                hotelImg.setHotel(hotel);
                hotelImg.setUrl(imageUrl);
                hotelImgs.add(hotelImg);
            } catch (Exception e) {
                return false;
            }
        }
        hotelImgRepo.saveAllAndFlush(hotelImgs);
        return true;
    }
}
