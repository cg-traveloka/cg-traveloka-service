package com.cgtravelokaservice.service.implement;

import com.cgtravelokaservice.entity.airplant.FlightInformation;
import com.cgtravelokaservice.repo.FlightInformationRepo;
import org.springframework.stereotype.Service;

@Service
public class FlightInformationService {

    private final FlightInformationRepo flightInformationRepo;


    public FlightInformationService(FlightInformationRepo flightInformationRepo) {
        this.flightInformationRepo = flightInformationRepo;
    }

    public void saveFlightInformation(FlightInformation flightInformation) {
        flightInformationRepo.save(flightInformation);
    }

}
