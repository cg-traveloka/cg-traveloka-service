package com.cgtravelokaservice.controller;

import com.cgtravelokaservice.repo.BedTypeRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BedTypeController {
    @Autowired
    private BedTypeRepo bedTypeRepo;

    @GetMapping("/api/bed-types")
    public ResponseEntity<?> findAll() {
        return ResponseEntity.ok().body(bedTypeRepo.findAll(Sort.by("name")));
    }
}
