package com.cgtravelokaservice.service.implement;

import com.cgtravelokaservice.dto.request.HotelSearchDTO;
import com.cgtravelokaservice.dto.response.HotelsResponeDTO;
import com.cgtravelokaservice.entity.booking.RoomContract;
import com.cgtravelokaservice.entity.hotel.Hotel;
import com.cgtravelokaservice.entity.hotel.HotelImg;
import com.cgtravelokaservice.entity.room.Room;
import com.cgtravelokaservice.repo.CityRepo;
import com.cgtravelokaservice.repo.HotelImgRepo;
import com.cgtravelokaservice.repo.HotelRepo;
import com.cgtravelokaservice.repo.RoomRepo;
import com.cgtravelokaservice.service.IHotelService;
import com.cgtravelokaservice.service.IImageService;
import com.cgtravelokaservice.service.IRoomContractService;
import com.cgtravelokaservice.util.IConvertUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
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
    @Autowired
    HotelRepo hotelRepo;
    @Autowired
    RoomRepo roomRepo;
    @Autowired
    IRoomContractService roomContractService;
    @Autowired
    IConvertUtil convertUtil;

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
        if (hotel.getDefaultImg() == null) {
            hotel.setDefaultImg(hotelImgs.get(0).getUrl());
        }
        hotelImgRepo.saveAllAndFlush(hotelImgs);
        return true;
    }

    public HotelsResponeDTO search(HotelSearchDTO hotelSearchDTO) {
        Integer cityId =
                hotelSearchDTO.getCityId();
        List <Integer> hotelStars =
                hotelSearchDTO.getHotelStars();
        Integer personQuantity =
                hotelSearchDTO.getPersonQuantity() / hotelSearchDTO.getQuantity();
        Integer minPrice =
                hotelSearchDTO.getMinPrice();
        Integer maxPrice =
                hotelSearchDTO.getMaxPrice();
        Integer quantity =
                hotelSearchDTO.getQuantity();
        Sort sort =
                Sort.by("hotel" + ".hotelBookedNumbers").descending();
        switch (hotelSearchDTO.getSort()) {
            case "booked":
                break;
            case "point":
                sort =
                        Sort.by("hotel" + ".averagePoint").descending();
                break;
            case "minPrice":
                sort =
                        Sort.by("hotel" + ".minSellPrice").ascending();
                break;
            case "maxPrice":
                sort =
                        Sort.by("hotel" + ".minSellPrice").descending();
                break;
            default:
                break;
        }
        Pageable pageable =
                PageRequest.of(hotelSearchDTO.getPageNumber(), 5, sort);
        Slice <Hotel> hotels =
                hotelRepo.search(cityId, hotelStars, personQuantity, minPrice, maxPrice, quantity, pageable);
        List <Hotel> hotelList =
                new ArrayList <>(hotels.getContent());
        for (int j =
             0; j < hotelList.size(); j++) {
            List <Room> rooms =
                    roomRepo.findAllByHotel(hotelList.get(j));
            for (int i =
                 0; i < rooms.size(); i++) {
                RoomContract roomContract =
                        convertUtil.convertToRoomContract(rooms.get(i), hotelSearchDTO);
                boolean isValid =
                        roomContractService.isContractValid(roomContract);
                if (! isValid) {
                    rooms.remove(rooms.get(i));
                    i--;
                }
            }
            if (rooms.isEmpty()) {
                hotelList.remove(hotelList.get(j));
                j--;
            }
        }
        HotelsResponeDTO hotelsResponeDTO =
                new HotelsResponeDTO();
        hotelsResponeDTO.setHotels(hotelList);
        hotelsResponeDTO.setPageNumber(hotelSearchDTO.getPageNumber());
        return hotelsResponeDTO;
    }
}
