package com.cgtravelokaservice.service;

import com.cgtravelokaservice.entity.airplant.AirPlantBrand;
import org.springframework.web.multipart.MultipartFile;


public interface IAirplaneBrandService {
    boolean setLogoUrl(AirPlantBrand airPlantBrand, MultipartFile logoUrl);
}
