package com.cgtravelokaservice.util.implement;

import com.cgtravelokaservice.dto.AirplaneBrandDto;
import com.cgtravelokaservice.entity.airplant.AirPlantBrand;
import com.cgtravelokaservice.service.implement.AirplaneBrandService;
import com.cgtravelokaservice.util.IConvertUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ConvertUtil implements IConvertUtil {
    @Autowired
    private AirplaneBrandService airplaneBrandService;

    @Override
    public AirPlantBrand airplaneBrandDtoToAirplaneBrand(AirplaneBrandDto airplaneBrandDto) {
        AirPlantBrand brand = new AirPlantBrand();
        brand.setName(airplaneBrandDto.getName());
        MultipartFile logoUrl = airplaneBrandDto.getLogoUrl();
        airplaneBrandService.setLogoUrl(brand, logoUrl);
        return brand;
    }
}
