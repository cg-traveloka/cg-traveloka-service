package com.cgtravelokaservice.service.implement;

import com.cgtravelokaservice.dto.FlightInformationDto;
import com.cgtravelokaservice.entity.airplant.FlightInformation;
import com.cgtravelokaservice.entity.airplant.SeatInformation;
import com.cgtravelokaservice.entity.airplant.SeatType;
import com.cgtravelokaservice.repo.SeatInformationRepo;
import com.cgtravelokaservice.repo.SeatTypeRepo;
import com.cgtravelokaservice.service.ISeatService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.time.Duration;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

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

    public SeatInformation getCheapestSeat(List<FlightInformation> flightInfos) {
        return flightInfos.stream()  // Stream over the flightInfos
                .flatMap(flightInfo -> convertFlightInfosToSeatInfo(flightInfo).stream()) // Convert each flightInfo to a list of seatInfos and flatten the lists
                .min(Comparator.comparingInt(SeatInformation::getUnitPrice)) // Find the minimum unit price
                .orElse(null); // Return null if the list is empty
    }

    public SeatInformation getFastestSeat(List<FlightInformation> flightInfos) {
        return flightInfos.stream()
                .flatMap(flightInfo -> convertFlightInfosToSeatInfo(flightInfo).stream())
                .min(Comparator.comparingLong(this::calculateFlightDuration))
                .orElse(null);
    }

    private long calculateFlightDuration(SeatInformation seatInfo) {
        FlightInformation flightInfo = seatInfo.getFlightInformation();
        return Duration.between(flightInfo.getStartTime(), flightInfo.getEndTime()).toMinutes();
    }

    public List<SeatInformation> convertFlightInfosToSeatInfo(FlightInformation flightInfo) {
        return seatInformationRepo.findAllByFlightInformation(flightInfo);
    }
}
