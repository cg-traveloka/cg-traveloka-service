package com.cgtravelokaservice.util.implement;

import com.cgtravelokaservice.dto.AirplaneBrandDto;
import com.cgtravelokaservice.dto.FlightInformationDto;
import com.cgtravelokaservice.dto.HotelRegisterFormDTO;
import com.cgtravelokaservice.dto.RoomRegisterFormDTO;
import com.cgtravelokaservice.entity.airplant.AirPlantBrand;
import com.cgtravelokaservice.entity.airplant.FlightInformation;
import com.cgtravelokaservice.entity.hotel.Hotel;
import com.cgtravelokaservice.entity.room.Room;
import com.cgtravelokaservice.repo.AirplaneBrandRepo;
import com.cgtravelokaservice.repo.AirportLocationRepo;
import com.cgtravelokaservice.repo.BedTypeRepo;
import com.cgtravelokaservice.repo.CityRepo;
import com.cgtravelokaservice.repo.HotelImgRepo;
import com.cgtravelokaservice.repo.HotelRepo;
import com.cgtravelokaservice.repo.RoomTypeRepo;
import com.cgtravelokaservice.service.IImageService;
import com.cgtravelokaservice.service.implement.AirplaneBrandService;
import com.cgtravelokaservice.service.implement.SeatService;
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
    private SeatService seatService;
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





}
