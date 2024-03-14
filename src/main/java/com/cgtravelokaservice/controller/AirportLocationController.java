package com.cgtravelokaservice.controller;

import com.cgtravelokaservice.entity.airplant.AirPortLocation;
import com.cgtravelokaservice.service.IAirPortLocationService;
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

    private final IAirPortLocationService airPortLocationService;

    public AirPortLocationController(IAirPortLocationService airPortLocationService) {this.airPortLocationService = airPortLocationService;}

    @GetMapping("/api/airport-location/{city_name}")
    public ResponseEntity<?> getAirPortLocationByCity_Name(@Validated @PathVariable("city_name") String city_name, BindingResult bindingResult){
        if (bindingResult.hasFieldErrors()) {
            return ResponseEntity.badRequest().body("Request không hợp lệ");
        }
        try {
            List<AirPortLocation> locations = airPortLocationService.getAirPortLocationByCity_Name(city_name);
            return new ResponseEntity<>(locations, HttpStatus.OK);
        } catch (Exception e) {
            return  ResponseEntity.internalServerError().body("Đã xảy lỗi khi tìm kiếm sân bay");
        }
    }
}
