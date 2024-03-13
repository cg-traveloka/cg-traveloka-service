package com.cgtravelokaservice.service.implement;

import com.cgtravelokaservice.dto.request.RoomBookingRequestDTO;
import com.cgtravelokaservice.dto.response.RoomBookingResponse;
import com.cgtravelokaservice.entity.booking.RoomContract;
import com.cgtravelokaservice.entity.hotel.Hotel;
import com.cgtravelokaservice.entity.room.Room;
import com.cgtravelokaservice.repo.HotelRepo;
import com.cgtravelokaservice.repo.RoomRepo;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RoomBookingService {
    private final RoomRepo roomRepo;

    private final RoomContractService roomContractService;

    private final HotelRepo hotelRepo;

    public RoomBookingService(RoomRepo roomRepo, RoomContractService roomContractService, HotelRepo hotelRepo) {
        this.roomRepo = roomRepo;
        this.roomContractService = roomContractService;
        this.hotelRepo = hotelRepo;
    }


    public RoomBookingResponse displayListRoom(RoomBookingRequestDTO bookingRequest) {
        List<Room> availableRooms = new ArrayList<>();
        List<Room> rooms = roomRepo.findByHotelId(bookingRequest.getHotelId());

        for (Room room : rooms) {
            if (room.getQuantity() < bookingRequest.getRoomQuantity()) {
                throw new RuntimeException("Không còn phòng khả dụng");
            }
            RoomContract roomContract = new RoomContract();
            roomContract.setStartDate(bookingRequest.getStartDate());
            roomContract.setEndDate(bookingRequest.getEndDate());
            roomContract.setRoomQuantity(bookingRequest.getRoomQuantity());
            roomContract.setRoom(room);

            if (roomContractService.isContractValid(roomContract)) {
                availableRooms.add(room);
            }
        }
        Hotel hotel = hotelRepo.getReferenceById(bookingRequest.getHotelId());
        return new RoomBookingResponse(hotel, availableRooms);


//        roomContract.setStartDate(bookingRequest.getStartDate());
//        roomContract.setEndDate(bookingRequest.getEndDate());
//        roomContract.setRoomQuantity(bookingRequest.getRoomQuantity());
//        roomContract.setRoom(roomRepo.getReferenceById(bookingRequest.getRoomId()));
//
//
//        if (availableRooms.size() < bookingRequest.getRoomQuantity()) {
//            throw new RuntimeException("Không còn phòng khả dụng");
//        }
//
//
//        Hotel hotel = availableRooms.get(0).getHotel();
//        if (bookingRequest.getRoomQuantity() <= bookingRequest.getPersonQuantity()) {
//            return new RoomBookingResponse(hotel, availableRooms);
//        } else {
//            throw new RuntimeException("Số lượng người không phù hợp");
//        }
    }
}