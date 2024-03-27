package com.cgtravelokaservice.controller;

import com.cgtravelokaservice.repo.RoomUtilityRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RoomUtilityController {
    @Autowired
    private RoomUtilityRepo roomUtilityRepo;

    @GetMapping("/api/room-utilities/{roomUtilityTypeId}")
    public ResponseEntity<?> findAllByRoomUtilTypeId(@PathVariable Integer roomUtilityTypeId) {
        return ResponseEntity.ok().body(roomUtilityRepo.findAllByRoomUtilityType_Id(roomUtilityTypeId,
                Sort.by("name")));
    }
}
