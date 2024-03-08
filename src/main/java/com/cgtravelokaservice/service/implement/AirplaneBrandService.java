package com.cgtravelokaservice.service.implement;

import com.cgtravelokaservice.dto.AirplaneBrandDto;
import com.cgtravelokaservice.entity.airplant.AirPlantBrand;
import com.cgtravelokaservice.service.IAirplaneBrandService;
import com.cgtravelokaservice.service.IImageService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class AirplaneBrandService implements IAirplaneBrandService {
    private final IImageService imageService;

    public AirplaneBrandService(IImageService imageService) {
        this.imageService = imageService;
    }

    @Override
    public AirPlantBrand convertToAirplaneBrand(AirplaneBrandDto airplaneBrandDto) {
        AirPlantBrand brand = new AirPlantBrand();
        brand.setName(airplaneBrandDto.getName());
        brand.setLogoUrl(String.valueOf(airplaneBrandDto.getLogoUrl()));
        return brand;
    }

    @Override
    public boolean setLogoUrl(AirPlantBrand airPlantBrand, MultipartFile logoUrl) {
        try {
            String url = imageService.save(logoUrl);
            AirPlantBrand brand = new AirPlantBrand();
            brand.setLogoUrl(url);
        } catch (Exception e) {
            return false;
        }
        return true;
    }
}
