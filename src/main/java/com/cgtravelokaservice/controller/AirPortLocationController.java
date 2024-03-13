package com.cgtravelokaservice.controller;

import com.cgtravelokaservice.entity.airplant.AirPortLocation;
import com.cgtravelokaservice.service.IAirPortLocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class AirPortLocationController {


    @Autowired
    private IAirPortLocationService airPortLocationService;


    @GetMapping("/api/airport/location/{city_name}")
    public ResponseEntity<?> getAirPortLocationByCity_Name(@Validated @PathVariable("city_name") String city_name, BindingResult bindingResult){
        if (bindingResult.hasFieldErrors()) {
            return ResponseEntity.badRequest().body("Your request is not valid. Check again your username or password");
        }
        try {
            List<AirPortLocation> locations = airPortLocationService.getAirPortLocationByCity_Name(city_name);
            return new ResponseEntity<>(locations, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("An error occurred: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
