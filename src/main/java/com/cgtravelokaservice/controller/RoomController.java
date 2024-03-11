package com.cgtravelokaservice.controller;

import com.cgtravelokaservice.dto.RoomRegisterFormDTO;
import com.cgtravelokaservice.entity.room.Room;
import com.cgtravelokaservice.repo.RoomRepo;
import com.cgtravelokaservice.util.IConvertUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RoomController {
    @Autowired
    IConvertUtil convertUtil;
    @Autowired
    RoomRepo roomRepo;

    @PostMapping("/api/rooms")
    public ResponseEntity <?> registerRoom(@RequestBody RoomRegisterFormDTO roomRegisterFormDTO) {
        Room room =
                convertUtil.roomRegisterFormToRoom(roomRegisterFormDTO);
        room = roomRepo.save(room);
        
    }
}
