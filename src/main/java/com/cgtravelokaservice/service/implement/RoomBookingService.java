package com.cgtravelokaservice.service.implement;

import com.cgtravelokaservice.dto.request.RoomBookingRequestDTO;
import com.cgtravelokaservice.dto.response.RoomBookingResponse;
import com.cgtravelokaservice.entity.booking.RoomContract;
import com.cgtravelokaservice.entity.room.Room;
import com.cgtravelokaservice.repo.RoomRepo;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RoomBookingService {
    private final RoomRepo roomRepo;

    private final RoomContractService
            roomContractService;

    public RoomBookingService(RoomRepo roomRepo, RoomContractService roomContractService) {
        this.roomRepo = roomRepo;
        this.roomContractService =
                roomContractService;
    }

    public RoomBookingResponse displayListRoom(RoomBookingRequestDTO bookingRequest) {
        List <Room> availableRooms =
                new ArrayList <>();
        List <Room> rooms =
                roomRepo.findByHotelId(bookingRequest.getHotelId());

        for (Room room : rooms) {
            RoomContract roomContract =
                    new RoomContract();
            roomContract.setStartDate(bookingRequest.getStartDate());
            roomContract.setEndDate(bookingRequest.getEndDate());
            roomContract.setRoomQuantity(bookingRequest.getRoomQuantity());
            roomContract.setRoom(room);
            Integer personPerRoom =
                    bookingRequest.getPersonQuantity() / bookingRequest.getRoomQuantity();
            if (roomContractService.isContractValid(roomContract) && personPerRoom <= room.getMaxPerson()) {
                availableRooms.add(room);
            }
        }
        return new RoomBookingResponse(availableRooms);
    }
}