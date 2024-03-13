package com.cgtravelokaservice.service.implement;

import com.cgtravelokaservice.dto.request.RoomBookingRequestDTO;
import com.cgtravelokaservice.dto.response.RoomBookingResponseDTO;
import com.cgtravelokaservice.entity.booking.RoomContract;
import com.cgtravelokaservice.entity.hotel.Hotel;
import com.cgtravelokaservice.entity.room.Room;
import com.cgtravelokaservice.repo.HotelRepo;
import com.cgtravelokaservice.repo.RoomContractRepo;
import com.cgtravelokaservice.repo.RoomRepo;
import com.cgtravelokaservice.service.IRoomContractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RoomBookingService {


    @Autowired
    private RoomContractRepo roomContractRepo;

    @Autowired
    private RoomRepo roomRepo;

    @Autowired
    private HotelRepo hotelRepo;

    @Autowired
    private IRoomContractService roomContractService;
    public RoomBookingResponseDTO bookRoom(RoomBookingRequestDTO bookingRequest) {

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
            System.out.println(roomContract);
            if (roomContractService.isContractValid(roomContract)) {
                availableRooms.add(room);
                System.out.println(availableRooms);
            }
        }
        Hotel hotel = hotelRepo.getReferenceById(bookingRequest.getHotelId());
        return new RoomBookingResponseDTO(availableRooms,hotel);
    }

}
