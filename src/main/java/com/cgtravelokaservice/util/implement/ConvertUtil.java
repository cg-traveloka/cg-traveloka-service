package com.cgtravelokaservice.util.implement;

import com.cgtravelokaservice.dto.AirplaneBrandDto;
import com.cgtravelokaservice.dto.FlightInformationDto;
import com.cgtravelokaservice.dto.HotelRegisterFormDTO;
import com.cgtravelokaservice.dto.RoomRegisterFormDTO;
import com.cgtravelokaservice.dto.TicketAirplaneDto;
import com.cgtravelokaservice.dto.request.HotelSearchDTO;
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
import com.cgtravelokaservice.util.IConvertUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

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
    private RoomRepo roomRepo;


    @Autowired
    private SeatInformationRepo seatInformationRepo;

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
    public FlightInformation convertToNewFlightInformation(FlightInformationDto flightInformationDto) {
        FlightInformation flightInformation =
                new FlightInformation();
        flightInformation.setStartTime(flightInformationDto.getStartTime());
        flightInformation.setEndTime(flightInformationDto.getEndTime());
        flightInformation.setFromAirPortLocation(airportLocationRepo.getReferenceById(flightInformationDto.getFromAirportLocationId()));
        flightInformation.setToAirPortLocation(airportLocationRepo.getReferenceById(flightInformationDto.getToAirportLocationId()));
        flightInformation.setAirPlantBrand(airplaneBrandRepo.getReferenceById(flightInformationDto.getAirplaneBrandId()));
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


    public TicketAirPlant ticketAirPlantDtoToTicketAirPlant(TicketAirplaneDto ticketAirplaneDto) {
        TicketAirPlant ticketAirPlant = new TicketAirPlant();
        ticketAirPlant.setQuantity(ticketAirplaneDto.getQuantity());
        SeatInformation seatInformation =
                seatInformationRepo.getReferenceById(ticketAirplaneDto.getSeatInfoId());
        ticketAirPlant.setSeatType(seatInformation.getSeatType());
        ticketAirPlant.setFlightInformation(seatInformation.getFlightInformation());
        Integer totalPrice = seatInformation.getUnitPrice() * ticketAirplaneDto.getQuantity();
        ticketAirPlant.setTotalMoney(totalPrice);
        return ticketAirPlant;
    }

    public RoomContract convertToRoomContract(Room room, HotelSearchDTO hotelSearchDTO) {
        RoomContract roomContract =
                new RoomContract();
        roomContract.setRoom(room);
        roomContract.setRoomQuantity(hotelSearchDTO.getQuantity());
        roomContract.setStartDate(hotelSearchDTO.getStartDate());
        roomContract.setEndDate(hotelSearchDTO.getEndDate());
        return roomContract;

    }
}
