package com.cgtravelokaservice.controller;

import com.cgtravelokaservice.repo.RoomUtilityTypeRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RoomUtilityTypeController {
    @Autowired
    private RoomUtilityTypeRepo roomUtilityTypeRepo;

    @GetMapping("/api/room-utility-types")
    public ResponseEntity<?> findAll() {
        return ResponseEntity.ok().body(roomUtilityTypeRepo.findAll(Sort.by("name")));
    }
}
