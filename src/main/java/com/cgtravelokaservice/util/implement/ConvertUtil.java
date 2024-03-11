package com.cgtravelokaservice.util.implement;

import com.cgtravelokaservice.dto.AirplaneBrandDto;
import com.cgtravelokaservice.dto.FlightInformationDto;
import com.cgtravelokaservice.entity.airplant.AirPlantBrand;
import com.cgtravelokaservice.entity.airplant.FlightInformation;
import com.cgtravelokaservice.repo.AirplaneBrandRepo;
import com.cgtravelokaservice.repo.AirportLocationRepo;
import com.cgtravelokaservice.service.implement.AirplaneBrandService;
import com.cgtravelokaservice.util.IConvertUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ConvertUtil implements IConvertUtil {
    @Autowired
    private AirplaneBrandService airplaneBrandService;
    @Autowired
    private AirportLocationRepo airportLocationRepo;
    @Autowired
    private AirplaneBrandRepo airplaneBrandRepo;

    @Override
    public AirPlantBrand airplaneBrandDtoToAirplaneBrand(AirplaneBrandDto airplaneBrandDto) {
        AirPlantBrand brand = new AirPlantBrand();
        brand.setName(airplaneBrandDto.getName());
        MultipartFile logoUrl = airplaneBrandDto.getLogoUrl();
        airplaneBrandService.setLogoUrl(brand, logoUrl);
        return brand;
    }

    @Override
    public FlightInformation convertToNewFlightInformation(FlightInformationDto flightInformationDto) {
        FlightInformation flightInformation = new FlightInformation();
        flightInformation.setStartTime(flightInformationDto.getStartTime());
        flightInformation.setEndTime(flightInformationDto.getEndTime());
        flightInformation.setFromAirPortLocation(airportLocationRepo.getReferenceById(flightInformationDto.getFromAirportLocationId()));
        flightInformation.setToAirPortLocation(airportLocationRepo.getReferenceById(flightInformationDto.getToAirportLocationId()));
        flightInformation.setAirPlantBrand(airplaneBrandRepo.getReferenceById(flightInformationDto.getAirplaneBrandId()));
        return flightInformation;
    }
}
