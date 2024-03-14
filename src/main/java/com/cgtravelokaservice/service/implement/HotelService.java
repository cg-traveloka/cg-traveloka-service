package com.cgtravelokaservice.service.implement;

import com.cgtravelokaservice.dto.request.HotelSearchDTO;
import com.cgtravelokaservice.dto.request.RoomContractRegisterFormDTO;
import com.cgtravelokaservice.dto.response.HotelsResponseDTO;
import com.cgtravelokaservice.entity.booking.RoomContract;
import com.cgtravelokaservice.entity.hotel.Hotel;
import com.cgtravelokaservice.entity.hotel.HotelImg;
import com.cgtravelokaservice.entity.hotel.HotelReview;
import com.cgtravelokaservice.entity.room.Room;
import com.cgtravelokaservice.repo.HotelImgRepo;
import com.cgtravelokaservice.repo.HotelRepo;
import com.cgtravelokaservice.repo.HotelReviewRepo;
import com.cgtravelokaservice.repo.RoomRepo;
import com.cgtravelokaservice.service.IHotelService;
import com.cgtravelokaservice.service.IImageService;
import com.cgtravelokaservice.util.IConvertUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class HotelService implements IHotelService {
    @Autowired
    private IImageService imageService;
    @Autowired
    private HotelImgRepo hotelImgRepo;
    @Autowired
    private HotelRepo hotelRepo;
    @Autowired
    private HotelReviewRepo hotelReviewRepo;
    @Autowired
    private RoomRepo roomRepo;
    @Autowired
    private RoomContractService roomContractService;

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
    @Autowired
    IConvertUtil convertUtil;

    public boolean setImagesForHotel(Hotel hotel, List<MultipartFile> files) {
        List<HotelImg> hotelImgs =
                new ArrayList<>();
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

    @Override
    public Slice<Hotel> getHotelsSortedByHotelBookedNumbers(Pageable pageable) {
        return hotelRepo.findAllByOrderByHotelBookedNumbersDesc(pageable);
    }

    @Override
    public Double calculateAverageRatingPoints(Integer hotelId) {
        List<HotelReview> reviews = hotelReviewRepo.findByHotelId(hotelId);
        if (reviews.isEmpty()) {
            return null;
        }
        int totalRatingPoints = reviews.stream().mapToInt(HotelReview::getRatingPoint).sum();
        return (double) totalRatingPoints / reviews.size();
    }

    @Override
    public Slice<Hotel> getHotels(Pageable pageable) {
        Slice<Hotel> allHotels = hotelRepo.findAllByOrderByHotelBookedNumbersDesc(pageable);
        List<Hotel> availableHotels = new ArrayList<>();

        for (Hotel hotel : allHotels) {
            List<Room> rooms = roomRepo.findByHotelId(hotel.getId());

            for (Room room : rooms) {
                RoomContract roomContract = new RoomContract();
                roomContract.setRoom(room);
                roomContract.setStartDate(LocalDate.now());
                roomContract.setEndDate(LocalDate.now().plusDays(1));
                roomContract.setRoomQuantity(1);
                if (roomContractService.isContractValid(roomContract)) {
                    availableHotels.add(hotel);
                    break;
                }
            }
        }
        return new SliceImpl<>(availableHotels, pageable, allHotels.hasNext());
    }

    public Room findSuitableRoom(RoomContractRegisterFormDTO roomRegister) {
        Room room = roomRepo.findById(roomRegister.getRoomId()).orElse(null);

        if (room != null) {
            RoomContract roomContract = new RoomContract();
            roomContract.setRoom(room);
            roomContract.setStartDate(roomRegister.getStartDate());
            roomContract.setEndDate(roomRegister.getEndDate());

            if (roomContractService.isContractValid(roomContract)) {
                return room;
            }
        }
        // Nếu không có phòng trống, ném ra ngoại lệ
        return null;
    }


    public HotelsResponseDTO search(HotelSearchDTO hotelSearchDTO) {
        Integer cityId =
                hotelSearchDTO.getCityId();
        List<Integer> hotelStars =
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
        Slice<Hotel> hotels =
                hotelRepo.search(cityId, hotelStars, personQuantity, minPrice, maxPrice, quantity, pageable);
        List<Hotel> hotelList =
                new ArrayList<>(hotels.getContent());
        for (int j =
             0; j < hotelList.size(); j++) {
            List<Room> rooms =
                    roomRepo.findAllByHotel(hotelList.get(j));
            for (int i =
                 0; i < rooms.size(); i++) {
                RoomContract roomContract =
                        convertUtil.convertToRoomContract(rooms.get(i), hotelSearchDTO);
                boolean isValid =
                        roomContractService.isContractValid(roomContract);
                if (!isValid) {
                    rooms.remove(rooms.get(i));
                    i--;
                }
            }
            if (rooms.isEmpty()) {
                hotelList.remove(hotelList.get(j));
                j--;
            }
        }
        HotelsResponseDTO hotelsResponseDTO =
                new HotelsResponseDTO();
        hotelsResponseDTO.setHotels(hotelList);
        hotelsResponseDTO.setPageNumber(hotelSearchDTO.getPageNumber());
        return hotelsResponseDTO;
    }
}
