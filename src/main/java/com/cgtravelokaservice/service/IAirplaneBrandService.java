package com.cgtravelokaservice.service;

import com.cgtravelokaservice.entity.airplant.AirPlantBrand;
import com.cgtravelokaservice.entity.airplant.FlightInformation;
import org.springframework.data.domain.Sort;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


public interface IAirplaneBrandService {
    boolean setLogoUrl(AirPlantBrand airPlantBrand, MultipartFile logoUrl);
    List<AirPlantBrand> findByFlightInfos(List<FlightInformation> flightInfos);
    List<AirPlantBrand> findAll();
    List<AirPlantBrand> findAllByNameContainsIgnoreCase(String name);

}
