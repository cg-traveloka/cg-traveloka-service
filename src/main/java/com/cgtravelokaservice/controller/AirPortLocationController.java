package com.cgtravelokaservice.controller;

import com.cgtravelokaservice.entity.airplant.AirPortLocation;
import com.cgtravelokaservice.service.IAirPortLocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class AirPortLocationController {


    @Autowired
    private IAirPortLocationService
            airPortLocationService;


    @GetMapping("/api/airport/{id}")
    public ResponseEntity <?> getAirPortLocationByCity_Name(@PathVariable("id") Integer cityId) {
        try {
            List <AirPortLocation> locations =
                    airPortLocationService.getAirPortLocationByCityId(cityId);
            return new ResponseEntity <>(locations, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity <>("An error occurred: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
