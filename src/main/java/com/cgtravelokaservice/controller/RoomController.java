package com.cgtravelokaservice.controller;

import com.cgtravelokaservice.dto.RoomRegisterFormDTO;
import com.cgtravelokaservice.dto.request.RoomBookingRequestDTO;
import com.cgtravelokaservice.dto.response.RoomBookingResponse;
import com.cgtravelokaservice.entity.hotel.Hotel;
import com.cgtravelokaservice.entity.room.Room;
import com.cgtravelokaservice.entity.room.RoomRoomUtility;
import com.cgtravelokaservice.entity.room.RoomUtility;
import com.cgtravelokaservice.repo.HotelRepo;
import com.cgtravelokaservice.repo.RoomRepo;
import com.cgtravelokaservice.repo.RoomRoomUtilityRepo;
import com.cgtravelokaservice.repo.RoomUtilityRepo;
import com.cgtravelokaservice.service.IRoomService;
import com.cgtravelokaservice.service.implement.RoomBookingService;
import com.cgtravelokaservice.util.IConvertUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class RoomController {
    @Autowired
    private IConvertUtil convertUtil;
    @Autowired
    private RoomRepo roomRepo;
    @Autowired
    private RoomUtilityRepo roomUtilityRepo;
    @Autowired
    private RoomRoomUtilityRepo
            roomRoomUtilityRepo;
    @Autowired
    private IRoomService roomService;
    @Autowired
    private HotelRepo hotelRepo;
    @Autowired
    private RoomBookingService roomBookingService;


    @PostMapping(value = "/api/rooms", consumes = "multipart/form-data")
    public ResponseEntity <?> registerRoom(@ModelAttribute RoomRegisterFormDTO roomRegisterFormDTO) {
//        Tạo room entity
        Room room =
                convertUtil.roomRegisterFormToRoom(roomRegisterFormDTO);
        room = roomRepo.save(room);

//        Set min price of room cho Hotel
        Integer min = room.getUnitPriceSell();
        Hotel hotel = room.getHotel();
        hotel.setMinOriginPrice(room.getUnitPriceOrigin());
        hotel.setMinSellPrice(room.getUnitPriceSell());
        List <Room> rooms =
                roomRepo.findAllByHotel(room.getHotel());
        for (Room room1 : rooms) {
            if (room1.getUnitPriceSell() < min) {
                hotel.setMinSellPrice(room1.getUnitPriceSell());
                hotel.setMinOriginPrice(room1.getUnitPriceOrigin());
            }
        }
        hotelRepo.save(hotel);

//      Set utility cho room
        List <RoomRoomUtility> roomRoomUtilities =
                new ArrayList <>();
        List <Integer> roomUtilities =
                roomRegisterFormDTO.getRoomUtilityId();
        for (Integer utilityId : roomUtilities) {
            RoomUtility roomUtility =
                    roomUtilityRepo.getReferenceById(utilityId);
            RoomRoomUtility roomRoomUtility =
                    new RoomRoomUtility();
            roomRoomUtility.setRoomUtility(roomUtility);
            roomRoomUtility.setRoom(room);
            roomRoomUtilities.add(roomRoomUtility);
        }
        roomRoomUtilityRepo.saveAllAndFlush(roomRoomUtilities);
//        Set img cho room
        roomService.setImgForRoom(room, roomRegisterFormDTO.getImages());
        return ResponseEntity.ok().body(room);
    }

//    @PostMapping(value = "/api/rooms/book")
//    public ResponseEntity<?> bookRoom(@ModelAttribute RoomBookingRequestDTO bookingRequest) {
//        RoomBookingResponse roomBookingResponse =
//                roomBookingService.bookRoom(bookingRequest);
//        return ResponseEntity.ok().body(roomBookingResponse);
//    }

    @GetMapping(value = "/api/rooms")
    public ResponseEntity <?> getRooms(@ModelAttribute RoomBookingRequestDTO request) {
        try {
            if (request.getPersonQuantity() < request.getRoomQuantity()) {
                return ResponseEntity.badRequest().body("Số lượng người phải lớn hơn hoặc bằng số phòng");
            }
            RoomBookingResponse
                    roomBookingResponse =
                    roomBookingService.displayListRoom(request);
            return ResponseEntity.ok().body(roomBookingResponse);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping(value = "/api/room/{id}")
    public ResponseEntity <?> getRoom(@PathVariable("id") Integer id) {
        Room room =
                roomRepo.findById(id).orElse(null);
        return ResponseEntity.ok().body(room);
    }
}
