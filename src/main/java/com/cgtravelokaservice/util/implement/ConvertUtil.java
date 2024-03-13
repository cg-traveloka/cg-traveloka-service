package com.cgtravelokaservice.util.implement;

import com.cgtravelokaservice.dto.AirplaneBrandDto;
import com.cgtravelokaservice.dto.FlightInformationDetailedDto;
import com.cgtravelokaservice.dto.FlightInfoSearchDTO;
import com.cgtravelokaservice.dto.FlightInformationRegisterDto;
import com.cgtravelokaservice.dto.HotelRegisterFormDTO;
import com.cgtravelokaservice.dto.RoomRegisterFormDTO;
import com.cgtravelokaservice.dto.SeatDetailsDto;
import com.cgtravelokaservice.dto.request.RoomContractRegisterFormDTO;
import com.cgtravelokaservice.entity.airplant.AirPlantBrand;
import com.cgtravelokaservice.entity.airplant.FlightInformation;
import com.cgtravelokaservice.entity.airplant.SeatInformation;
import com.cgtravelokaservice.entity.booking.RoomContract;
import com.cgtravelokaservice.entity.booking.TicketAirPlant;
import com.cgtravelokaservice.entity.hotel.Hotel;
import com.cgtravelokaservice.entity.room.Room;
import com.cgtravelokaservice.repo.AirplaneBrandRepo;
import com.cgtravelokaservice.repo.AirportLocationRepo;
import com.cgtravelokaservice.repo.BedTypeRepo;
import com.cgtravelokaservice.repo.CityRepo;
import com.cgtravelokaservice.repo.HotelImgRepo;
import com.cgtravelokaservice.repo.HotelRepo;
import com.cgtravelokaservice.repo.RoomRepo;
import com.cgtravelokaservice.repo.RoomTypeRepo;
import com.cgtravelokaservice.repo.SeatInformationRepo;
import com.cgtravelokaservice.service.IImageService;
import com.cgtravelokaservice.service.implement.AirplaneBrandService;
import com.cgtravelokaservice.service.implement.SeatService;
import com.cgtravelokaservice.util.IConvertUtil;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.Duration;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ConvertUtil implements IConvertUtil {
    @Autowired
    private AirplaneBrandService
            airplaneBrandService;
    @Autowired
    CityRepo cityRepo;
    @Autowired
    IImageService imageService;
    @Autowired
    HotelImgRepo hotelImgRepo;
    @Autowired
    RoomTypeRepo roomTypeRepo;
    @Autowired
    HotelRepo hotelRepo;
    @Autowired
    BedTypeRepo bedTypeRepo;
    @Autowired
    private AirportLocationRepo
            airportLocationRepo;
    @Autowired
    private AirplaneBrandRepo airplaneBrandRepo;

    @Autowired
    private SeatInformationRepo seatInformationRepo;
    @Autowired
    private SeatService seatService;
    private RoomRepo roomRepo;

    private final ModelMapper modelMapper = new ModelMapper();

    @Override
    public AirPlantBrand airplaneBrandDtoToAirplaneBrand(AirplaneBrandDto airplaneBrandDto) {
        AirPlantBrand brand = new AirPlantBrand();
        brand.setName(airplaneBrandDto.getName());
        MultipartFile logoUrl =
                airplaneBrandDto.getLogoUrl();
        airplaneBrandService.setLogoUrl(brand, logoUrl);
        return brand;
    }


    public Hotel hotelRegisterFormToHotel(HotelRegisterFormDTO hotelRegisterForm) {
        Hotel hotel = new Hotel();
        hotel.setHotelName(hotelRegisterForm.getHotelName());
        hotel.setDescription(hotelRegisterForm.getDescription());
        hotel.setHotelStar(hotelRegisterForm.getHotelStar());
        hotel.setAddress(hotelRegisterForm.getAddress());
        hotel.setHotelBookedNumbers(0);
        hotel.setCity(cityRepo.getReferenceById(hotelRegisterForm.getCityId()));
        return hotel;
    }

    public Room roomRegisterFormToRoom(RoomRegisterFormDTO roomRegisterFormDTO) {
        Room room = new Room();
        room.setRoomType(roomTypeRepo.getReferenceById(roomRegisterFormDTO.getRoomTypeId()));
        room.setHotel(hotelRepo.getReferenceById(roomRegisterFormDTO.getHotelId()));
        room.setSize(roomRegisterFormDTO.getSize());
        room.setQuantity(roomRegisterFormDTO.getQuantity());
        room.setBedType(bedTypeRepo.getReferenceById(roomRegisterFormDTO.getBedTypeId()));
        room.setMaxPerson(roomRegisterFormDTO.getMaxPerson());
        room.setUnitPriceOrigin(roomRegisterFormDTO.getUnitPriceOrigin());
        room.setUnitPriceSell(roomRegisterFormDTO.getUnitPriceSell());
        return room;
    }

    @Override
    public FlightInformation convertToNewFlightInformation(FlightInformationRegisterDto flightInformationRegisterDto) {
        FlightInformation flightInformation =
                new FlightInformation();
        flightInformation.setStartTime(flightInformationRegisterDto.getStartTime());
        flightInformation.setEndTime(flightInformationRegisterDto.getEndTime());
        flightInformation.setFromAirPortLocation(airportLocationRepo.getReferenceById(flightInformationRegisterDto.getFromAirportLocationId()));
        flightInformation.setToAirPortLocation(airportLocationRepo.getReferenceById(flightInformationRegisterDto.getToAirportLocationId()));
        flightInformation.setAirPlantBrand(airplaneBrandRepo.getReferenceById(flightInformationRegisterDto.getAirplaneBrandId()));
        return flightInformation;
    }


    public RoomContract roomContractFormDTOToRoomContract(RoomContractRegisterFormDTO roomContractRegisterFormDTO) {
        RoomContract roomContract =
                new RoomContract();
        roomContract.setRoom(roomRepo.getReferenceById(roomContractRegisterFormDTO.getRoomId()));
        roomContract.setRoomQuantity(roomContractRegisterFormDTO.getRoomQuantity());
        roomContract.setStartDate(roomContractRegisterFormDTO.getStartDate());
        roomContract.setEndDate(roomContractRegisterFormDTO.getEndDate());
//        Tính tiền phòng
        Integer totalMoney =
                roomContractRegisterFormDTO.getRoomQuantity() * roomRepo.getReferenceById(roomContractRegisterFormDTO.getRoomId()).getUnitPriceSell();
        roomContract.setTotalMoney(totalMoney);
        return roomContract;
    }

@Override
    public FlightInfoSearchDTO convertToFlightDetailsDTO(FlightInformation flightInfo, Integer seatTypeId) {
        FlightInfoSearchDTO dto = modelMapper.map(flightInfo, FlightInfoSearchDTO.class);
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
    public FlightInformationDetailedDto convertToDetailedDto(FlightInformation flightInformation) {
        FlightInformationDetailedDto detailedDto = modelMapper.map(flightInformation, FlightInformationDetailedDto.class);
        detailedDto.setFlightDuration(Duration.between(flightInformation.getStartTime(), flightInformation.getEndTime()));
        detailedDto.setSeatDetails(convertSeatInformationToDto(flightInformation.getId()));
        return detailedDto;
    }

    public List<SeatDetailsDto> convertSeatInformationToDto(Integer flightId) {
        List<SeatInformation> seatInformationList = seatInformationRepo.findByFlightInformationId(flightId);
        return seatInformationList.stream()
                .map(seatInformation -> modelMapper.map(seatInformation, SeatDetailsDto.class))
                .collect(Collectors.toList());
    }


}
