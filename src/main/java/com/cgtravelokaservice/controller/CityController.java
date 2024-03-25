package com.cgtravelokaservice.controller;

import com.cgtravelokaservice.entity.city.City;
import com.cgtravelokaservice.repo.CityRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CityController {
    @Autowired
    CityRepo cityRepo;

    @GetMapping("/api/cities")
    public ResponseEntity <?> getCities() {
        List <City> cities = cityRepo.findAll();
        return ResponseEntity.ok().body(cities);
    }
}
