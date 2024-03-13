package com.cgtravelokaservice.service.implement;

import com.cgtravelokaservice.dto.request.RoomContractRegisterFormDTO;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
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
}
