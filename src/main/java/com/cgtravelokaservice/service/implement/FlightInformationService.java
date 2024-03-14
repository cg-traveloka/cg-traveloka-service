package com.cgtravelokaservice.service.implement;

import com.cgtravelokaservice.dto.AirPlantSearchDTO;
import com.cgtravelokaservice.dto.FlightInfoSearchDTO;
import com.cgtravelokaservice.dto.FlightInForShortDescription;
import com.cgtravelokaservice.dto.request.SearchFlightDetailsRequestDTO;
import com.cgtravelokaservice.dto.response.SearchFlightResponse;
import com.cgtravelokaservice.entity.airplant.AirPlantBrand;
import com.cgtravelokaservice.entity.airplant.FlightInformation;
import com.cgtravelokaservice.repo.FlightInformationRepo;
import com.cgtravelokaservice.util.IConvertUtil;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

import com.cgtravelokaservice.service.IFlightInformationService;
import org.springframework.stereotype.Service;

import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service

public class FlightInformationService implements IFlightInformationService {

    private final FlightInformationRepo flightInformationRepo;

    private final AirplaneBrandService airplaneBrandService;

    private final SeatService seatService;
    private final IConvertUtil convertUtil;


    public FlightInformationService(FlightInformationRepo flightInformationRepo, AirplaneBrandService airplaneBrandService, SeatService seatService, IConvertUtil convertUtil) {

        this.flightInformationRepo = flightInformationRepo;
        this.airplaneBrandService = airplaneBrandService;
        this.seatService = seatService;
        this.convertUtil = convertUtil;


    }

    @Override

    public void saveFlightInformation(FlightInformation flightInformation) {
        flightInformationRepo.save(flightInformation);
    }


    @Override
    public List<FlightInfoSearchDTO> searchFlights(SearchFlightDetailsRequestDTO request, Pageable pageable) {
        assert request.getSortBy() != null;
        if (request.getSortBy().equalsIgnoreCase("duration")) {
            return searchFlightsSortByDuration(request, pageable);
        }
        Slice<FlightInformation> flightInformationSlice = flightInformationRepo.search(
                request.getFromAirportLocationId(),
                request.getToAirportLocationId(),
                request.getStartTime(),
                request.getAirPlantBrandId(),
                request.getSeatTypeId(),
                request.getSeatQuantity(),
                request.getSortBy(),
                request.getOrder(),
                request.getPriceFrom(),
                request.getPriceTo(),
                pageable);
        List<FlightInformation> flightInformation = new ArrayList<>(flightInformationSlice.getContent());
        return flightInformation.stream().map(flightInfo -> convertUtil.convertToFlightDetailsDTO(flightInfo,
                request.getSeatTypeId())).toList();
    }


    @Override
    public List<FlightInformation> searchList(SearchFlightDetailsRequestDTO request) {
        return flightInformationRepo.searchForList(
                request.getFromAirportLocationId(),
                request.getToAirportLocationId(),
                request.getStartTime(),
                request.getAirPlantBrandId(),
                request.getSeatTypeId(),
                request.getSeatQuantity()
        );
    }

    @Override
    public SearchFlightResponse loadSearchFlightResponse(SearchFlightDetailsRequestDTO request) {

        Pageable pageable = PageRequest.of(0, 10);
        List<FlightInfoSearchDTO> flightDetailsDTO = searchFlights(request, pageable);
        List<FlightInformation> flightInformationList = searchList(request);
        List<AirPlantBrand> airPlantBrands = airplaneBrandService.findByFlightInfos(flightInformationList);

        List<AirPlantSearchDTO> airPlantSearchDTO = airPlantBrands.stream()
                .map(brand -> new AirPlantSearchDTO(brand.getId(), brand.getName(), brand.getLogoUrl()))
                .collect(Collectors.toList());

        String value1 = seatService.getLowestPriceSeat(flightInformationList).getUnitPrice().toString();
        String value2 = seatService.getShortestFlight(flightInformationList).getUnitPrice().toString();

        FlightInForShortDescription description =
                FlightInForShortDescription.builder().name("Gía thấp nhất").unitPrice(value1).build();
        FlightInForShortDescription description2 = FlightInForShortDescription.builder().name("Thời gian bay ngắn " +
                "nhất").unitPrice(value2).build();
        List<FlightInForShortDescription> descriptions = new ArrayList<>();
        descriptions.add(description);
        descriptions.add(description2);
        return new SearchFlightResponse(flightDetailsDTO, airPlantSearchDTO, descriptions);
    }

    private List<FlightInfoSearchDTO> searchFlightsSortByDuration(SearchFlightDetailsRequestDTO request,
                                                                  Pageable pageable) {
        List<FlightInformation> flights = searchList(request);
        List<FlightInformation> sortedFlights = new ArrayList<>(flights);
        sortedFlights.sort(Comparator.comparingLong(flight ->
                ChronoUnit.MINUTES.between(flight.getStartTime(), flight.getEndTime())));
        int max = Math.min((pageable.getPageNumber() + 1) * pageable.getPageSize(), flights.size());
        List<FlightInformation> result = sortedFlights.subList(pageable.getPageNumber(), max);
        return result.stream()
                .map(flight -> convertUtil.convertToFlightDetailsDTO(flight, request.getSeatTypeId()))
                .toList();

    }

}
