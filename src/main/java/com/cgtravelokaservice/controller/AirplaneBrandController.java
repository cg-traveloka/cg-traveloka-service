package com.cgtravelokaservice.controller;

import com.cgtravelokaservice.dto.AirplaneBrandDto;
import com.cgtravelokaservice.dto.request.SearchFlightDetailsRequestDTO;
import com.cgtravelokaservice.entity.airplant.AirPlantBrand;
import com.cgtravelokaservice.entity.airplant.FlightInformation;
import com.cgtravelokaservice.repo.AirplaneBrandRepo;
import com.cgtravelokaservice.service.IAirplaneBrandService;
import com.cgtravelokaservice.service.IFlightInformationService;
import com.cgtravelokaservice.util.implement.ConvertUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
public class AirplaneBrandController {
    private final IAirplaneBrandService airplaneBrandService;

    private final AirplaneBrandRepo airplaneBrandRepo;

    private final IFlightInformationService flightInFormationService;
    private final ConvertUtil convertUtil;

    public AirplaneBrandController(IAirplaneBrandService airplaneBrandService, AirplaneBrandRepo airplaneBrandRepo, IFlightInformationService flightInFormationService, ConvertUtil convertUtil) {
        this.airplaneBrandService = airplaneBrandService;
        this.airplaneBrandRepo = airplaneBrandRepo;
        this.flightInFormationService = flightInFormationService;
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


    @GetMapping("/api/airplane-brands/search")
    public ResponseEntity<?> searchFlights(@RequestBody SearchFlightDetailsRequestDTO searchFlightDetailsRequestDTO){
        try {
            List<FlightInformation> flightInformation = flightInFormationService.searchList(
                    searchFlightDetailsRequestDTO
            );

            List<AirPlantBrand> airPlantBrands = airplaneBrandService.findByFlightInfos(flightInformation);

            return new ResponseEntity<>(airPlantBrands, HttpStatus.OK);
        } catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>("An error occurred while searching for flights.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}