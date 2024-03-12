package com.cgtravelokaservice.service.implement;

import com.cgtravelokaservice.entity.airplant.AirPlantBrand;
import com.cgtravelokaservice.entity.airplant.FlightInformation;
import com.cgtravelokaservice.service.IAirplaneBrandService;
import com.cgtravelokaservice.service.IImageService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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

    public List<AirPlantBrand> findByFlightInfos(List<FlightInformation> flightInfos) {
        List<AirPlantBrand> result = new ArrayList<>();

        flightInfos.forEach(flightInfo -> {
            AirPlantBrand airPlantBrand = flightInfo.getAirPlantBrand();
            if (result.isEmpty() || result.stream().noneMatch(brand -> Objects.equals(brand.getId(), airPlantBrand.getId()))) {
                result.add(airPlantBrand);
            }
        });

        return result;
    }
}
