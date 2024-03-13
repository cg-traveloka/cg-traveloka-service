package com.cgtravelokaservice.controller;

import com.cgtravelokaservice.service.implement.AirportLocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AirportLocationController {
    @Autowired
    private AirportLocationService airportLocationService;

    @GetMapping("/api/airport-locations/{city}")
    public ResponseEntity<?> getAirportLocationsByCity(@PathVariable("city") String city) {
        try {
            return ResponseEntity.ok(airportLocationService.findByCityName(city));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body("Error during fetching airport locations by city");
        }

    }
}
