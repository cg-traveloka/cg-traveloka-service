package com.cgtravelokaservice.controller;

import com.cgtravelokaservice.entity.room.RoomRoomUtility;
import com.cgtravelokaservice.repo.RoomRoomUtilityRepo;
import com.cgtravelokaservice.repo.RoomUtilityRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class RoomUtilityController {
    @Autowired
    private RoomRoomUtilityRepo
            roomRoomUtilityRepo;
    @Autowired
    private RoomUtilityRepo roomUtilityRepo;

    @GetMapping("/api/room/utilities")
    public ResponseEntity <?> getRoomUtility(@ModelAttribute("roomId") Integer roomId) {
        List <RoomRoomUtility> utilities =
                roomRoomUtilityRepo.findAllByRoomId(roomId);
        return ResponseEntity.ok().body(utilities);
    }

    @GetMapping("/api/room-utilities/{roomUtilityTypeId}")
    public ResponseEntity <?> findAllByRoomUtilTypeId(@PathVariable Integer roomUtilityTypeId) {
        return ResponseEntity.ok().body(roomUtilityRepo.findAllByRoomUtilityType_Id(roomUtilityTypeId, Sort.by("name")));
    }
}