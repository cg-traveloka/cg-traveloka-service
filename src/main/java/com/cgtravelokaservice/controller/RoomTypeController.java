package com.cgtravelokaservice.controller;

import com.cgtravelokaservice.repo.RoomTypeRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RoomTypeController {
    @Autowired
    private RoomTypeRepo roomTypeRepo;

    @GetMapping("/api/room-types")
    public ResponseEntity<?> findAll() {
        return ResponseEntity.ok().body(roomTypeRepo.findAll(Sort.by("name")));
    }
}
