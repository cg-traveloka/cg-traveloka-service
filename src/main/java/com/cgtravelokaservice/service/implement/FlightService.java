package com.cgtravelokaservice.service.implement;

import com.cgtravelokaservice.dto.FlightInformationDetailedDto;
import com.cgtravelokaservice.entity.airplant.FlightInformation;
import com.cgtravelokaservice.repo.AirplaneBrandRepo;
import com.cgtravelokaservice.repo.FlightInformationRepo;
import com.cgtravelokaservice.repo.SeatInformationRepo;
import com.cgtravelokaservice.service.IFlightService;
import com.cgtravelokaservice.util.IConvertUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;

@Service
public class FlightService implements IFlightService {
    private FlightInformationRepo flightInformationRepository;
    private SeatInformationRepo seatInformationRepository;
    private AirplaneBrandRepo airplaneBrandDto;
    private IConvertUtil convertUtil;

    @Autowired
    public FlightService(FlightInformationRepo flightInformationRepository, SeatInformationRepo seatInformationRepository, AirplaneBrandRepo airplaneBrandDto, IConvertUtil convertUtil) {
        this.flightInformationRepository = flightInformationRepository;
        this.seatInformationRepository = seatInformationRepository;
        this.airplaneBrandDto = airplaneBrandDto;
        this.convertUtil = convertUtil;
    }
    @Override
    public Slice<FlightInformationDetailedDto> getAllFlightsSortedByStartDate(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Slice<FlightInformation> allFlights = flightInformationRepository.findAllByOrderByStartTimeAsc(pageable);
        return allFlights.map(convertUtil::convertToDetailedDto);
    }






}