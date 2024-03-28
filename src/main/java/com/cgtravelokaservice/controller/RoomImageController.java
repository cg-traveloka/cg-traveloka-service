package com.cgtravelokaservice.controller;

import com.cgtravelokaservice.entity.room.RoomImg;
import com.cgtravelokaservice.repo.RoomImgRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class RoomImageController {
    @Autowired
    RoomImgRepo roomImgRepo;

    @GetMapping("/api/room/images")
    public ResponseEntity <?> getRoomImages(@ModelAttribute("roomId") Integer roomId) {
        List <RoomImg> images =
                roomImgRepo.findAllByRoomId(roomId);
        return ResponseEntity.ok().body(images);
    }
}
