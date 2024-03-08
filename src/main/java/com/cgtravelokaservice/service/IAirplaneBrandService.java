package com.cgtravelokaservice.service;

import com.cgtravelokaservice.dto.AirplaneBrandDto;
import com.cgtravelokaservice.entity.airplant.AirPlantBrand;
import org.springframework.web.multipart.MultipartFile;


public interface IAirplaneBrandService {
    AirPlantBrand convertToAirplaneBrand(AirplaneBrandDto airplaneBrandDto);

    boolean setLogoUrl(AirPlantBrand airPlantBrand, MultipartFile logoUrl);
}
