package com.cgtravelokaservice.service.implement;

import com.cgtravelokaservice.dto.FlightInformationRegisterDto;
import com.cgtravelokaservice.dto.request.GetAvailableSeatsRequest;
import com.cgtravelokaservice.dto.request.SearchFlightDetailsRequestDTO;
import com.cgtravelokaservice.entity.airplant.FlightInformation;
import com.cgtravelokaservice.entity.airplant.SeatInformation;
import com.cgtravelokaservice.entity.airplant.SeatType;
import com.cgtravelokaservice.repo.SeatInformationRepo;
import com.cgtravelokaservice.repo.SeatTypeRepo;
import com.cgtravelokaservice.service.ISeatService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class SeatService implements ISeatService {
    private final SeatInformationRepo
            seatInformationRepo;
    private final SeatTypeRepo seatTypeRepo;


    public SeatService(SeatInformationRepo seatInformationRepo, SeatTypeRepo seatTypeRepo) {
        this.seatInformationRepo =
                seatInformationRepo;
        this.seatTypeRepo = seatTypeRepo;
    }

    @Override

    public List <SeatInformation> createSeatsForNewFlight(FlightInformationRegisterDto flightInformationRegisterDto, FlightInformation flightInformation) {

        List <SeatInformation> seatsInformation =
                new ArrayList <>();
        List <SeatType> seatTypes =
                new ArrayList <>();

        for (int i = 1; i <= 4; i++) {
            Optional <SeatType> seatType =
                    seatTypeRepo.findById(i);
            seatType.ifPresent(seatTypes :: add);
        }

        for (SeatType seatType : seatTypes) {
            SeatInformation seatInformation =
                    createSeatForType(seatType, flightInformation, flightInformationRegisterDto);
            if (seatInformation != null) {
                seatsInformation.add(seatInformation);
                seatInformationRepo.save(seatInformation);
            }
        }
        return seatsInformation;
    }


    private SeatInformation createSeatForType(SeatType seatType, FlightInformation flightInformation, FlightInformationRegisterDto flightInformationRegisterDto) {
        return switch (seatType.getId()) {
            case 1 ->
                    createSeatInformation(seatType, flightInformation, flightInformationRegisterDto.getNormalSeatQuantity(), flightInformationRegisterDto.getNormalSeatPrice());
            case 2 ->
                    createSeatInformation(seatType, flightInformation, flightInformationRegisterDto.getSpecialNormalSeatQuantity(), flightInformationRegisterDto.getSpecialNormalSeatPrice());
            case 3 ->
                    createSeatInformation(seatType, flightInformation, flightInformationRegisterDto.getBusinessSeatQuantity(), flightInformationRegisterDto.getBusinessSeatPrice());
            case 4 ->
                    createSeatInformation(seatType, flightInformation, flightInformationRegisterDto.getVipSeatQuantity(), flightInformationRegisterDto.getVipSeatPrice());

            default -> null;
        };
    }

    private SeatInformation createSeatInformation(SeatType seatType, FlightInformation flightInformation, int quantity, int price) {
        SeatInformation seatInformation =
                new SeatInformation();
        seatInformation.setFlightInformation(flightInformation);
        seatInformation.setSeatType(seatType);
        seatInformation.setQuantity(quantity);
        seatInformation.setUnitPrice(price);
        return seatInformation;
    }


    public SeatInformation getLowestPriceSeat(List <FlightInformation> flightInformations, Integer seatTypeId) {
        return flightInformations.stream().map(flight -> seatInformationRepo.findByFlightInformationIdAndSeatTypeId(flight.getId(), seatTypeId)).filter(Optional :: isPresent).map(Optional :: get).min(Comparator.comparingInt(SeatInformation :: getUnitPrice)).orElseThrow(NoSuchElementException :: new);
    }


    public SeatInformation getShortestFlight(List <FlightInformation> flightInformations, Integer seatTypeId) {
        return flightInformations.stream().map(flight -> seatInformationRepo.findByFlightInformationIdAndSeatTypeId(flight.getId(), seatTypeId)).filter(Optional :: isPresent).map(Optional :: get).min(Comparator.comparing(seat -> Duration.between(seat.getFlightInformation().getStartTime(), seat.getFlightInformation().getEndTime()))).orElseThrow(NoSuchElementException :: new);
    }


    public List <SeatInformation> getAllAvailableSeatsByFlight(GetAvailableSeatsRequest request) {
        List <SeatInformation> result =
                new ArrayList <>();
        seatTypeRepo.findAll().forEach(seatType -> {
            Optional <SeatInformation> seatInfo =
                    seatInformationRepo.findByFlightInformationIdAndSeatTypeId(request.getFlightId(), seatType.getId());
            if (seatInfo.isPresent()) {
                if (seatInfo.get().getQuantity() >= request.getSeatQuantity()) {
                    result.add(seatInfo.get());
                }
            }
        });
        return result;
    }

    public List <SeatInformation> findAllAvailableSeatByRequest(SearchFlightDetailsRequestDTO flightRequest, Integer page) {
        Sort sort =
                Sort.by("flightInformation" + ".startTime").ascending();
        Pageable pageable =
                PageRequest.of(page, 5, sort);
        List <SeatInformation> seats =
                seatInformationRepo.findSeatByRequest(flightRequest.getFromAirportLocationId(), flightRequest.getToAirportLocationId(), flightRequest.getStartDate().atStartOfDay(), flightRequest.getSeatTypeId(), flightRequest.getSeatQuantity(), pageable);
        return seats;
    }


}




