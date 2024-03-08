package com.cgtravelokaservice.controller;

import com.cgtravelokaservice.dto.AirplaneBrandDto;
import com.cgtravelokaservice.entity.airplant.AirPlantBrand;
import com.cgtravelokaservice.repo.AirplaneBrandRepo;
import com.cgtravelokaservice.service.IAirplaneBrandService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class AirplaneBrandController {
    private final IAirplaneBrandService airplaneBrandService;

    private final AirplaneBrandRepo airplaneBrandRepo;

    public AirplaneBrandController(IAirplaneBrandService airplaneBrandService, AirplaneBrandRepo airplaneBrandRepo) {
        this.airplaneBrandService = airplaneBrandService;
        this.airplaneBrandRepo = airplaneBrandRepo;
    }

    @PostMapping(value = "/api/airplane-brands", consumes = "multipart/form-data")
    public ResponseEntity<?> createAirplaneBrand(@Validated @ModelAttribute AirplaneBrandDto airplaneBrandDto) {
        AirPlantBrand airPlantBrand = airplaneBrandService.convertToAirplaneBrand(airplaneBrandDto);
        airplaneBrandRepo.saveAndFlush(airPlantBrand);

        MultipartFile logoUrl = airplaneBrandDto.getLogoUrl();
        airplaneBrandService.setLogoUrl(airPlantBrand, logoUrl);

        return ResponseEntity.ok().body(airPlantBrand);
    }
}
