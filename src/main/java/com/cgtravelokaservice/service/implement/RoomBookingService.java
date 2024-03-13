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
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

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
    public RoomBookingResponseDTO bookRoom(RoomBookingRequestDTO request) {

        List<Room> allRooms = roomRepo.findAllByHotelId(request.getHotelId());
        List<Room> availableRooms = new ArrayList<>();
        for (Room room : allRooms) {
            RoomContract tempContract = new RoomContract();
            tempContract.setRoom(room);
            tempContract.setStartDate(request.getStartDate().toLocalDate());
            tempContract.setEndDate(request.getEndDate().toLocalDate());
            tempContract.setRoomQuantity(request.getRoomQuantity());
            if (roomContractService.isContractValid(tempContract)) {
                availableRooms.add(room);
            }
        }
        Hotel hotel = hotelRepo.findById(request.getHotelId()).orElse(null);
        return new RoomBookingResponseDTO(availableRooms, hotel);
    }

}
