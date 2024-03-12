package com.cgtravelokaservice.service.implement;

import com.cgtravelokaservice.dto.AirPlantSearchDTO;
import com.cgtravelokaservice.dto.FlightDetailsDTO;
import com.cgtravelokaservice.dto.FlightInForShortDescription;
import com.cgtravelokaservice.dto.request.FlightDetailsRequestDTO;
import com.cgtravelokaservice.dto.response.SearchFlightResponse;
import com.cgtravelokaservice.entity.airplant.AirPlantBrand;
import com.cgtravelokaservice.entity.airplant.FlightInformation;
import com.cgtravelokaservice.entity.airplant.SeatInformation;
import com.cgtravelokaservice.repo.FlightInformationRepo;
import com.cgtravelokaservice.repo.SeatInformationRepo;
import com.cgtravelokaservice.service.IFlightInFormationService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class FlightInformationService implements IFlightInFormationService {

    private final FlightInformationRepo flightInformationRepo;
    private final ModelMapper modelMapper = new ModelMapper();
    private final AirplaneBrandService airplaneBrandService;

    private  final SeatService seatService;
    private  final SeatInformationRepo seatInformationRepo;
    public FlightInformationService(FlightInformationRepo flightInformationRepo, AirplaneBrandService airplaneBrandService, SeatService seatService, SeatInformationRepo seatInformationRepo) {
        this.flightInformationRepo = flightInformationRepo;
        this.airplaneBrandService = airplaneBrandService;
        this.seatService = seatService;
        this.seatInformationRepo = seatInformationRepo;
    }
    @Override

    public void saveFlightInformation(FlightInformation flightInformation) {
        flightInformationRepo.save(flightInformation);
    }

    @Override
    public Slice<FlightDetailsDTO> searchFlights(FlightDetailsRequestDTO request, Pageable pageable) {
        Slice<FlightInformation> flightInformation = flightInformationRepo.search(
                request.getFromAirportLocationId(),
                request.getToAirportLocationId(),
                request.getStartTime(),
                request.getAirPlantBrandId(),
                request.getSeatTypeId(),
                request.getSeatQuantity(),
                request.getSortBy(),
                request.getOrder(),
                request.getDurationFrom(),
                request.getDurationTo(),
                request.getPriceFrom(),
                request.getPriceTo(),
                pageable);

        return flightInformation.map(flightInfo -> convertToDTO(flightInfo, request.getSeatTypeId()));
    }


    @Override
    public List<FlightInformation> searchList(FlightDetailsRequestDTO request){

        return flightInformationRepo.searchForList(
                request.getFromAirportLocationId(),
                request.getToAirportLocationId(),
                request.getStartTime(),
                request.getAirPlantBrandId(),
                request.getSeatTypeId(),
                request.getSeatQuantity()

        );
    }

    private FlightDetailsDTO convertToDTO(FlightInformation flightInfo, Integer seatTypeId) {
        FlightDetailsDTO dto = modelMapper.map(flightInfo, FlightDetailsDTO.class);
        Optional<SeatInformation> optionalSeatInfo = seatInformationRepo.findByFlightInformationIdAndSeatTypeId(flightInfo.getId(), seatTypeId);

        if (optionalSeatInfo.isPresent()) {
            SeatInformation seatInfo = optionalSeatInfo.get();
            dto.setSeatQuantity(seatInfo.getQuantity());
            dto.setSeatTypeName(seatInfo.getSeatType().getName());
            dto.setUnitPrice(seatInfo.getUnitPrice());
            dto.setTimeInterval(Duration.between(flightInfo.getStartTime(), flightInfo.getEndTime()).toMinutes());
        }

        return dto;
    }
    @Override
    public SearchFlightResponse loadSearchFlightResponse(FlightDetailsRequestDTO request) {

        Pageable pageable = PageRequest.of(0,10);
        Slice<FlightDetailsDTO> flightDetailsDTO = searchFlights(request, pageable);
        List<FlightInformation> flightInformationList = searchList(request);
        List<AirPlantBrand> airPlantBrands = airplaneBrandService.findByFlightInfos(flightInformationList);

        List<AirPlantSearchDTO> airPlantSearchDTO = airPlantBrands.stream()
                .map(brand -> new AirPlantSearchDTO(brand.getId(), brand.getName(), brand.getLogoUrl()))
                .collect(Collectors.toList());

        String value1 = seatService.getLowestPriceSeat(flightInformationList).getUnitPrice().toString();
        String value2 = seatService.getShortestFlight(flightInformationList).getUnitPrice().toString();

        FlightInForShortDescription description = FlightInForShortDescription.builder().name("Gía thấp nhất").unitPrice(value1).build();
        FlightInForShortDescription description2 = FlightInForShortDescription.builder().name("Thời gian bay ngắn nhất").unitPrice(value2).build();
    List<FlightInForShortDescription> descriptions= new ArrayList<>();
    descriptions.add(description);
    descriptions.add(description2);
        return new SearchFlightResponse(flightDetailsDTO, airPlantSearchDTO, descriptions);
    }
}
