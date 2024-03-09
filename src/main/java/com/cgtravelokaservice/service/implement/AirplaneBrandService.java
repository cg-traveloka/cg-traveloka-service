package com.cgtravelokaservice.service.implement;

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
    public boolean setLogoUrl(AirPlantBrand airPlantBrand, MultipartFile logoUrl) {
        try {
            String url = imageService.save(logoUrl);
            airPlantBrand.setLogoUrl(url);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
