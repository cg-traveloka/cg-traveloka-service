package com.cgtravelokaservice.controller;

import com.cgtravelokaservice.dto.AirplaneBrandDto;
import com.cgtravelokaservice.entity.airplant.AirPlantBrand;
import com.cgtravelokaservice.repo.AirplaneBrandRepo;
import com.cgtravelokaservice.service.IAirplaneBrandService;
import com.cgtravelokaservice.util.implement.ConvertUtil;
import org.springframework.http.HttpStatus;
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

    private final ConvertUtil convertUtil;

    public AirplaneBrandController(IAirplaneBrandService airplaneBrandService, AirplaneBrandRepo airplaneBrandRepo, ConvertUtil convertUtil) {
        this.airplaneBrandService = airplaneBrandService;
        this.airplaneBrandRepo = airplaneBrandRepo;
        this.convertUtil = convertUtil;
    }

    @PostMapping(value = "/api/airplane-brands", consumes = "multipart/form-data")
    public ResponseEntity<?> createAirplaneBrand(@Validated @ModelAttribute AirplaneBrandDto airplaneBrandDto) {
        try {
            AirPlantBrand airPlantBrand = convertUtil.airplaneBrandDtoToAirplaneBrand(airplaneBrandDto);
            airPlantBrand = airplaneBrandRepo.saveAndFlush(airPlantBrand);
            MultipartFile logoUrl = airplaneBrandDto.getLogoUrl();
            airplaneBrandService.setLogoUrl(airPlantBrand, logoUrl);
            return ResponseEntity.ok().body(airPlantBrand);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Không thể tạo hãng máy bay mới, vui lòng thử lại sau.");
        }
    }
}