package com.cgtravelokaservice.service.implement;

import com.cgtravelokaservice.dto.FlightInFoShortDescription;
import com.cgtravelokaservice.dto.FlightInfoDtoForSearch;
import com.cgtravelokaservice.dto.request.SearchFlightRequest;
import com.cgtravelokaservice.dto.AirPlaneSearchDto;
import com.cgtravelokaservice.dto.response.SearchFlightResponse;
import com.cgtravelokaservice.entity.airplant.AirPlantBrand;
import com.cgtravelokaservice.entity.airplant.FlightInformation;
import com.cgtravelokaservice.entity.airplant.SeatInformation;
import com.cgtravelokaservice.repo.FlightInformationRepo;
import com.cgtravelokaservice.service.IFlightInformationService;
import com.cgtravelokaservice.repo.SeatInformationRepo;
import com.cgtravelokaservice.repo.SeatTypeRepo;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.modelmapper.TypeToken;

import java.lang.reflect.Type;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class FlightInformationService implements IFlightInformationService {
    private final FlightInformationRepo flightInformationRepo;

    private final SeatInformationRepo seatInformationRepo;

    private final SeatTypeRepo seatTypeRepo;

    @Autowired
    private AirplaneBrandService airplaneBrandService;

    @Autowired
    private SeatService seatService;

    private final ModelMapper modelMapper = new ModelMapper();


    public FlightInformationService(FlightInformationRepo flightInformationRepo,
                                    SeatInformationRepo seatInformationRepo,
                                    SeatTypeRepo seatTypeRepo
    ) {

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
                , request.getAirplaneId(),request.getSortBy(), request.getOrder(), request.getDurationFrom(),
                request.getDurationTo(), request.getPriceFrom(), request.getPriceTo(),
                pageable);
        return flightInformation.map(flightInfo -> convertToDTO(flightInfo, request.getSeatTypeId()));
    }

    private FlightInfoDtoForSearch convertToDTO(FlightInformation flightInfo, Integer seatTypeId) {
        Optional<SeatInformation> seatInformation =
                seatInformationRepo.findByFlightInformation_IdAndSeatType_Id(flightInfo.getId(), seatTypeId);
        return seatInformation.map(information -> FlightInfoDtoForSearch.builder().flightInfoId(flightInfo.getId())
                .seatInfoId(seatInformation.get().getId())
                .airPlaneBrand(modelMapper.map(flightInfo.getAirPlantBrand(), AirPlaneSearchDto.class))
                .fromAirportLocation(flightInfo.getFromAirPortLocation().getName())
                .toAirportLocation(flightInfo.getToAirPortLocation().getName())
                .startTime(flightInfo.getStartTime())
                .endTime(flightInfo.getEndTime())
                .timeInterval(Duration.between(flightInfo.getStartTime(), flightInfo.getEndTime()).toMinutes())
                .unitPrice(information.getUnitPrice())
                .seatType(seatTypeRepo.getReferenceById(seatTypeId).getName()).build()).orElse(null);
    }

    public List<FlightInformation> search(SearchFlightRequest request) {
        return flightInformationRepo.searchToList(request.getFromLocationId(),
                request.getToLocationId(), request.getStartTime(), request.getSeatQuantity(), request.getSeatTypeId()
                , request.getAirplaneId());
    }

    public SearchFlightResponse createFirstSearchResponse(SearchFlightRequest request) {
        Pageable pageable = PageRequest.of(0, 10);
        request.setSortBy("start_time");
        Slice<FlightInfoDtoForSearch> flightInformation = search(request, pageable);
        List<FlightInformation> list = search(request);
        List<AirPlantBrand> airPlantBrands = airplaneBrandService.findByFlightInfos(list);
        Type listType = new TypeToken<List<AirPlaneSearchDto>>() {}.getType();
        List<AirPlaneSearchDto> airPlaneSearchDtos =
                modelMapper.map(airPlantBrands, listType);
        String value1 = seatService.getCheapestSeat(list).getUnitPrice().toString();
        String value2 = seatService.getFastestSeat(list).getUnitPrice().toString();
        FlightInFoShortDescription des1 =
                FlightInFoShortDescription.builder().name("Gía thấp nhất").value(value1).build();
        FlightInFoShortDescription des2 =
                FlightInFoShortDescription.builder().name("Thời gian bay ngắn nhất").value(value2).build();
        List<FlightInFoShortDescription> descriptions = new ArrayList<>();
        descriptions.add(des1);
        descriptions.add(des2);
        return new SearchFlightResponse(airPlaneSearchDtos, flightInformation,descriptions);
    }


}
