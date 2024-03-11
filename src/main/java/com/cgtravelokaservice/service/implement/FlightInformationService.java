package com.cgtravelokaservice.service.implement;

import com.cgtravelokaservice.dto.FlightInformationDto;
import com.cgtravelokaservice.entity.airplant.FlightInformation;
import com.cgtravelokaservice.repo.AirplaneBrandRepo;
import com.cgtravelokaservice.repo.AirportLocationRepo;
import com.cgtravelokaservice.repo.FlightInformationRepo;
import com.cgtravelokaservice.service.IFlightInformationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FlightInformationService implements IFlightInformationService {
    private final AirplaneBrandRepo
            airplaneBrandRepo;

    private final AirportLocationRepo
            airportLocationRepo;
    private final FlightInformationRepo
            flightInformationRepo;

    @Autowired
    public FlightInformationService(AirplaneBrandRepo airplaneBrandRepo, AirportLocationRepo airportLocationRepo, FlightInformationRepo flightInformationRepo) {
        this.airplaneBrandRepo =
                airplaneBrandRepo;
        this.airportLocationRepo =
                airportLocationRepo;
        this.flightInformationRepo =
                flightInformationRepo;
    }

    @Override
    public FlightInformation convertToNewFlightInformation(FlightInformationDto flightInformationDto) {
        FlightInformation flightInformation =
                new FlightInformation();
        flightInformation.setStartTime(flightInformationDto.getStartTime());
        flightInformation.setEndTime(flightInformationDto.getEndTime());
        flightInformation.setFromAirPortLocation(airportLocationRepo.getReferenceById(flightInformationDto.getFromAirportLocationId()));
        flightInformation.setToAirPortLocation(airportLocationRepo.getReferenceById(flightInformationDto.getToAirportLocationId()));
        flightInformation.setAirPlantBrand(airplaneBrandRepo.getReferenceById(flightInformationDto.getAirplaneBrandId()));

        return flightInformation;
    }

    public void saveFlightInformation(FlightInformation flightInformation) {
        flightInformationRepo.save(flightInformation);
    }

}
