package com.cgtravelokaservice.controller;

import com.cgtravelokaservice.dto.RoomRegisterFormDTO;
import com.cgtravelokaservice.entity.room.Room;
import com.cgtravelokaservice.entity.room.RoomRoomUtility;
import com.cgtravelokaservice.entity.room.RoomUtility;
import com.cgtravelokaservice.repo.RoomRepo;
import com.cgtravelokaservice.repo.RoomRoomUtilityRepo;
import com.cgtravelokaservice.repo.RoomUtilityRepo;
import com.cgtravelokaservice.service.IRoomService;
import com.cgtravelokaservice.util.IConvertUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
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


    @PostMapping(value = "/api/rooms", consumes = "multipart/form-data")
    public ResponseEntity <?> registerRoom(@ModelAttribute RoomRegisterFormDTO roomRegisterFormDTO) {
//        Tạo room entity
        Room room =
                convertUtil.roomRegisterFormToRoom(roomRegisterFormDTO);
        room = roomRepo.save(room);
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
}
