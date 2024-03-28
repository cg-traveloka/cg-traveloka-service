package com.cgtravelokaservice.service.implement;


import com.cgtravelokaservice.entity.airplant.AirPlantBrand;
import com.cgtravelokaservice.entity.airplant.FlightInformation;
import com.cgtravelokaservice.repo.AirplaneBrandRepo;
import com.cgtravelokaservice.service.IAirplaneBrandService;
import com.cgtravelokaservice.service.IImageService;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class AirplaneBrandService implements IAirplaneBrandService {
    private final IImageService imageService;

    private final AirplaneBrandRepo airplaneBrandRepo;


    public AirplaneBrandService(IImageService imageService, AirplaneBrandRepo airplaneBrandRepo) {
        this.imageService = imageService;
        this.airplaneBrandRepo = airplaneBrandRepo;

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

    @Override
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

    @Override
    public List<AirPlantBrand> findAllByNameContainsIgnoreCase(String name) {
        return airplaneBrandRepo.findAllByNameContainsIgnoreCase(name, Sort.by("name"));
    }

    public List<AirPlantBrand> findAll() {
        return airplaneBrandRepo.findAll(Sort.by("name"));
    }

}
