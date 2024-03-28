package com.cgtravelokaservice.controller;

import com.cgtravelokaservice.entity.room.RoomRoomUtility;
import com.cgtravelokaservice.repo.RoomRoomUtilityRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class RoomUtilityController {
    @Autowired
    RoomRoomUtilityRepo roomRoomUtilityRepo;

    @GetMapping("/api/room/utilities")
    public ResponseEntity <?> getRoomUtility(@ModelAttribute("roomId") Integer roomId) {
        List <RoomRoomUtility> utilities =
                roomRoomUtilityRepo.findAllByRoomId(roomId);
        return ResponseEntity.ok().body(utilities);
    }
}
