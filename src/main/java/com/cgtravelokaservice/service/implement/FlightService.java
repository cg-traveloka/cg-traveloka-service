package com.cgtravelokaservice.service.implement;

import com.cgtravelokaservice.dto.DetailedFlightInformationDto;
import com.cgtravelokaservice.dto.SeatDetailsDto;
import com.cgtravelokaservice.entity.airplant.FlightInformation;
import com.cgtravelokaservice.entity.airplant.SeatInformation;
import com.cgtravelokaservice.repo.AirplaneBrandRepo;
import com.cgtravelokaservice.repo.FlightInformationRepo;
import com.cgtravelokaservice.repo.SeatInformationRepo;
import com.cgtravelokaservice.service.IFlightInFormationService;
import com.cgtravelokaservice.service.IFlightService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.List;
import java.util.stream.Collectors;
@Service
public class FlightService implements IFlightService {
    private FlightInformationRepo flightInformationRepository;
    private SeatInformationRepo seatInformationRepository;
    private AirplaneBrandRepo airplaneBrandDto;
    private final ModelMapper modelMapper = new ModelMapper();

    @Autowired
    public FlightService(FlightInformationRepo flightInformationRepository, SeatInformationRepo seatInformationRepository, AirplaneBrandRepo airplaneBrandDto) {
        this.flightInformationRepository = flightInformationRepository;
        this.seatInformationRepository = seatInformationRepository;
        this.airplaneBrandDto = airplaneBrandDto;
    }
    @Override
    public Slice<DetailedFlightInformationDto> getAllFlightsSortedByStartDate(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Slice<FlightInformation> allFlights = flightInformationRepository.findAllByOrderByStartTimeAsc(pageable);
        return allFlights.map(this::convertToDetailedDto);
    }

    private DetailedFlightInformationDto convertToDetailedDto(FlightInformation flightInformation) {
        DetailedFlightInformationDto detailedDto = modelMapper.map(flightInformation, DetailedFlightInformationDto.class);
        detailedDto.setFlightDuration(calculateFlightDuration(flightInformation));
        detailedDto.setSeatDetails(convertSeatInformationToDto(flightInformation.getId()));
        return detailedDto;
    }

    private Duration calculateFlightDuration(FlightInformation flightInformation) {
        return Duration.between(flightInformation.getStartTime(), flightInformation.getEndTime());
    }

    private List<SeatDetailsDto> convertSeatInformationToDto(Integer flightId) {
        List<SeatInformation> seatInformationList = seatInformationRepository.findByFlightInformationId(flightId);
        return seatInformationList.stream()
                .map(seatInformation -> modelMapper.map(seatInformation, SeatDetailsDto.class))
                .collect(Collectors.toList());
    }

}