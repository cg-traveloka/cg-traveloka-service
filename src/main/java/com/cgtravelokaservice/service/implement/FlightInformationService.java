package com.cgtravelokaservice.service.implement;

import com.cgtravelokaservice.dto.FlightInfoDtoForSearch;
import com.cgtravelokaservice.dto.request.SearchFlightRequest;
import com.cgtravelokaservice.entity.airplant.FlightInformation;
import com.cgtravelokaservice.entity.airplant.SeatInformation;
import com.cgtravelokaservice.repo.FlightInformationRepo;
import com.cgtravelokaservice.repo.SeatInformationRepo;
import com.cgtravelokaservice.repo.SeatTypeRepo;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Optional;

@Service
public class FlightInformationService {
    private final FlightInformationRepo flightInformationRepo;

    private final SeatInformationRepo seatInformationRepo;

    private final SeatTypeRepo seatTypeRepo;


    public FlightInformationService(FlightInformationRepo flightInformationRepo,
                                    SeatInformationRepo seatInformationRepo,
                                    SeatTypeRepo seatTypeRepo) {

        this.flightInformationRepo = flightInformationRepo;
        this.seatInformationRepo = seatInformationRepo;
        this.seatTypeRepo = seatTypeRepo;
    }

    public void saveFlightInformation(FlightInformation flightInformation) {
        flightInformationRepo.save(flightInformation);
    }

    public Slice<FlightInfoDtoForSearch> search(SearchFlightRequest request, Pageable pageable) {
        Slice<FlightInformation> flightInformation = flightInformationRepo.search(request.getFromLocationId(),
                request.getToLocationId(), request.getStartTime(), request.getSeatQuantity(), request.getSeatTypeId()
                , request.getAirplaneId(), pageable);

        return flightInformation.map(flightInfo -> convertToDTO(flightInfo, request.getSeatTypeId()));
    }

    private FlightInfoDtoForSearch convertToDTO(FlightInformation flightInfo, Integer seatTypeId) {
        Optional<SeatInformation> seatInformation =
                seatInformationRepo.findByFlightInformation_IdAndSeatType_Id(flightInfo.getId(), seatTypeId);
        return seatInformation.map(information -> FlightInfoDtoForSearch.builder().flightInfoId(flightInfo.getId())
                .seatInfoId(seatInformation.get().getId())
                .airPlaneBrandName(flightInfo.getAirPlantBrand().getName())
                .fromAirportLocation(flightInfo.getFromAirPortLocation().getName())
                .toAirportLocation(flightInfo.getToAirPortLocation().getName())
                .startTime(flightInfo.getStartTime())
                .endTime(flightInfo.getEndTime())
                .timeInterval(Duration.between(flightInfo.getStartTime(), flightInfo.getEndTime()).toMinutes())
                .unitPrice(information.getUnitPrice())
                .seatType(seatTypeRepo.getReferenceById(seatTypeId).getName()).build()).orElse(null);

    }
}
