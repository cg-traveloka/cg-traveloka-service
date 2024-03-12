package com.cgtravelokaservice.service.implement;

import com.cgtravelokaservice.dto.FlightDetailsDTO;
import com.cgtravelokaservice.dto.FlightInForShortDescription;
import com.cgtravelokaservice.dto.FlightInformationDto;
import com.cgtravelokaservice.entity.airplant.FlightInformation;
import com.cgtravelokaservice.entity.airplant.SeatInformation;
import com.cgtravelokaservice.entity.airplant.SeatType;
import com.cgtravelokaservice.repo.SeatInformationRepo;
import com.cgtravelokaservice.repo.SeatTypeRepo;
import com.cgtravelokaservice.service.ISeatService;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SeatService implements ISeatService {
    private final SeatInformationRepo seatInformationRepo;
    private final SeatTypeRepo seatTypeRepo;

    public SeatService(SeatInformationRepo seatInformationRepo, SeatTypeRepo seatTypeRepo) {
        this.seatInformationRepo = seatInformationRepo;
        this.seatTypeRepo = seatTypeRepo;
    }

    @Override
    public List<SeatInformation> createSeatsForNewFlight(FlightInformationDto flightInformationDto, FlightInformation flightInformation) {
        List<SeatInformation> seatsInformation = new ArrayList<>();
        List<SeatType> seatTypes = new ArrayList<>();

        for (int i = 1; i <= 4; i++) {
            Optional<SeatType> seatType = seatTypeRepo.findById(i);
            seatType.ifPresent(seatTypes::add);
        }

        for (SeatType seatType : seatTypes) {
            SeatInformation seatInformation = createSeatForType(seatType, flightInformation, flightInformationDto);
            if (seatInformation != null) {
                seatsInformation.add(seatInformation);
                seatInformationRepo.save(seatInformation);
            }
        }
        return seatsInformation;
    }

    private SeatInformation createSeatForType(SeatType seatType, FlightInformation flightInformation, FlightInformationDto flightInformationDto) {
        return switch (seatType.getId()) {
            case 1 ->
                    createSeatInformation(seatType, flightInformation, flightInformationDto.getNormalSeatQuantity(), flightInformationDto.getNormalSeatPrice());
            case 2 ->
                    createSeatInformation(seatType, flightInformation, flightInformationDto.getSpecialNormalSeatQuantity(), flightInformationDto.getSpecialNormalSeatPrice());
            case 3 ->
                    createSeatInformation(seatType, flightInformation, flightInformationDto.getBusinessSeatQuantity(), flightInformationDto.getBusinessSeatPrice());
            case 4 ->
                    createSeatInformation(seatType, flightInformation, flightInformationDto.getVipSeatQuantity(), flightInformationDto.getVipSeatPrice());
            default -> null;
        };
    }

    private SeatInformation createSeatInformation(SeatType seatType, FlightInformation flightInformation, int quantity, int price) {
        SeatInformation seatInformation = new SeatInformation();
        seatInformation.setFlightInformation(flightInformation);
        seatInformation.setSeatType(seatType);
        seatInformation.setQuantity(quantity);
        seatInformation.setUnitPrice(price);
        return seatInformation;
    }
    public SeatInformation getLowestPriceSeat(List<FlightInformation> flightInformations) {

        return flightInformations.stream()
                .flatMap(flight -> seatInformationRepo.findByFlightInformation_Id(flight.getId()).stream())
                .min(Comparator.comparing(SeatInformation::getUnitPrice))
                .orElseThrow(NoSuchElementException::new);
    }


    public SeatInformation getShortestFlight(List<FlightInformation> seatInformationList) {
        return seatInformationList.stream()
                .flatMap(flightInformation -> seatInformationRepo.findByFlightInformation_Id(flightInformation.getId()).stream())
                .min(Comparator.comparing(seat -> Duration.between(seat.getFlightInformation().getStartTime(), seat.getFlightInformation().getEndTime()).toMinutes()))
                .orElseThrow(NoSuchElementException::new);
    }
}
