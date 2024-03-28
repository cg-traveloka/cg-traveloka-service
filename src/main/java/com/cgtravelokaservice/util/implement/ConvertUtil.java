package com.cgtravelokaservice.util.implement;

import com.cgtravelokaservice.dto.*;
import com.cgtravelokaservice.dto.request.*;
import com.cgtravelokaservice.dto.response.ComboResponeDTO;
import com.cgtravelokaservice.dto.response.UnitComboResponeDTO;
import com.cgtravelokaservice.dto.response.UpdateProfileCustomerResponseDTO;
import com.cgtravelokaservice.entity.airplant.AirPlantBrand;
import com.cgtravelokaservice.entity.airplant.FlightInformation;
import com.cgtravelokaservice.entity.airplant.SeatInformation;
import com.cgtravelokaservice.entity.booking.RoomContract;
import com.cgtravelokaservice.entity.booking.TicketAirPlant;
import com.cgtravelokaservice.entity.hotel.Hotel;
import com.cgtravelokaservice.entity.hotel.HotelReview;
import com.cgtravelokaservice.entity.room.Room;
import com.cgtravelokaservice.entity.user.Customer;
import com.cgtravelokaservice.repo.AirplaneBrandRepo;
import com.cgtravelokaservice.repo.AirportLocationRepo;
import com.cgtravelokaservice.repo.BedTypeRepo;
import com.cgtravelokaservice.repo.CityRepo;
import com.cgtravelokaservice.repo.CustomerRepo;
import com.cgtravelokaservice.repo.HotelImgRepo;
import com.cgtravelokaservice.repo.HotelRepo;
import com.cgtravelokaservice.repo.RoomContractRepo;
import com.cgtravelokaservice.repo.RoomRepo;
import com.cgtravelokaservice.repo.RoomTypeRepo;
import com.cgtravelokaservice.repo.SeatInformationRepo;
import com.cgtravelokaservice.service.IImageService;
import com.cgtravelokaservice.service.implement.AirplaneBrandService;
import com.cgtravelokaservice.util.IConvertUtil;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.Duration;
import java.time.LocalDate;
import java.util.ArrayList;
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
    private SeatInformationRepo
            seatInformationRepo;
    @Autowired
    private RoomRepo roomRepo;
    @Autowired
    private CustomerRepo customerRepo;
    @Autowired
    private RoomContractRepo roomContractRepo;

    private final ModelMapper modelMapper =
            new ModelMapper();

    @Override
    public AirPlantBrand airplaneBrandDtoToAirplaneBrand(AirplaneBrandDto airplaneBrandDto) {
        AirPlantBrand brand = new AirPlantBrand();
        brand.setName(airplaneBrandDto.getName());
        MultipartFile logoUrl = airplaneBrandDto.getLogoImg();
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
        hotel.setAveragePoint(0.0);
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
        RoomContract roomContract = new RoomContract();
        System.out.println(roomContractRegisterFormDTO.getRoomId());
        roomContract.setRoom(roomRepo.getReferenceById(roomContractRegisterFormDTO.getRoomId()));
        roomContract.setRoomQuantity(roomContractRegisterFormDTO.getRoomQuantity());
        roomContract.setStartDate(roomContractRegisterFormDTO.getStartDate());
        roomContract.setEndDate(roomContractRegisterFormDTO.getEndDate());
        roomContract.setStatus("pending");
        roomContract.setEnableReview(false);
//        Tính tiền phòng
        int days =
                (int) Duration.between(roomContractRegisterFormDTO.getStartDate().atStartOfDay(), roomContractRegisterFormDTO.getEndDate().atStartOfDay()).toDays();
        Integer totalMoney =
                days * roomContractRegisterFormDTO.getRoomQuantity() * roomRepo.getReferenceById(roomContractRegisterFormDTO.getRoomId()).getUnitPriceSell();

        roomContract.setTotalMoney(totalMoney);
        return roomContract;
    }

    @Override

    public FlightInfoSearchDTO convertToFlightDetailsDTO(FlightInformation flightInfo, Integer seatTypeId) {
        FlightInfoSearchDTO dto =
                modelMapper.map(flightInfo, FlightInfoSearchDTO.class);
        Optional <SeatInformation>
                optionalSeatInfo =
                seatInformationRepo.findByFlightInformationIdAndSeatTypeId(flightInfo.getId(), seatTypeId);

        if (optionalSeatInfo.isPresent()) {
            SeatInformation seatInfo =
                    optionalSeatInfo.get();
            dto.setSeatQuantity(seatInfo.getQuantity());
            dto.setSeatTypeName(seatInfo.getSeatType().getName());
            dto.setUnitPrice(seatInfo.getUnitPrice());
            dto.setTimeInterval(Duration.between(flightInfo.getStartTime(), flightInfo.getEndTime()).toMinutes());
        }
        return dto;
    }

    @Override
    public FlightInformationDetailedDto convertToDetailedDto(FlightInformation flightInformation) {
        FlightInformationDetailedDto detailedDto =
                modelMapper.map(flightInformation, FlightInformationDetailedDto.class);
        detailedDto.setFlightDuration(Duration.between(flightInformation.getStartTime(), flightInformation.getEndTime()));
        detailedDto.setSeatDetails(convertSeatInformationToDto(flightInformation.getId()));
        return detailedDto;
    }

    public List <SeatDetailsDto> convertSeatInformationToDto(Integer flightId) {
        List <SeatInformation>
                seatInformationList =
                seatInformationRepo.findByFlightInformationId(flightId);
        return seatInformationList.stream().map(seatInformation -> modelMapper.map(seatInformation, SeatDetailsDto.class)).collect(Collectors.toList());
    }


    public RoomContract convertToRoomContract(Room room, HotelSearchDTO hotelSearchDTO) {
        RoomContract roomContract =
                new RoomContract();
        roomContract.setRoom(room);
        roomContract.setRoomQuantity(hotelSearchDTO.getRoomQuantity());
        roomContract.setStartDate(hotelSearchDTO.getStartDate());
        roomContract.setEndDate(hotelSearchDTO.getStartDate().plusDays(hotelSearchDTO.getNights()));
        return roomContract;

    }

    @Override
    public TicketAirPlant convertToTicketAirPlant(TicketAirPlaneDTO ticketDTO, SeatInformation seatInformation) {
        TicketAirPlant ticket =
                new TicketAirPlant();
        ticket.setFlightInformation(seatInformation.getFlightInformation());
        ticket.setSeatType(seatInformation.getSeatType());
        ticket.setQuantity(ticketDTO.getQuantity());
        ticket.setTotalMoney(seatInformation.getUnitPrice() * ticketDTO.getQuantity());
        return ticket;
    }

    public Customer convertDTOToCustomer(UpdateProfileCustomerRequestDTO requestDTO) {
        Customer customer =
                customerRepo.getReferenceById(requestDTO.getCustomerId());
        customer.setName(requestDTO.getName());
        customer.setGender(requestDTO.getGender());
        LocalDate dateOfBirth =
                LocalDate.of(requestDTO.getYear(), requestDTO.getMonth(), requestDTO.getDate());
        customer.setDateOfBirth(dateOfBirth);
        return customer;
    }

    public HotelReview convertDTOToHotelReview(ReviewRequestDTO reviewRequestDTO) {
        HotelReview hotelReview = new HotelReview();
        hotelReview.setRoomContract(roomContractRepo.getReferenceById(reviewRequestDTO.getContractId()));
        Double ratingPoint = reviewRequestDTO.getRatingPoint();
        hotelReview.setRatingPoint(Math.round(ratingPoint * 10) / 10.0);
        hotelReview.setComment(reviewRequestDTO.getComment());
        RoomContract roomContract = roomContractRepo.getReferenceById(reviewRequestDTO.getContractId());
        hotelReview.setRoomContract(roomContract);
        return hotelReview;
    }

    public RoomContractRegisterFormDTO convertToRoomContractRegisterFormDTO(ComboHasSeatAndHotelDTO comboHasSeatAndHotelDTO) {
        RoomContractRegisterFormDTO
                roomContractRegisterFormDTO =
                new RoomContractRegisterFormDTO();
        roomContractRegisterFormDTO.setRoomId(comboHasSeatAndHotelDTO.getRoomId());
        roomContractRegisterFormDTO.setRoomQuantity(comboHasSeatAndHotelDTO.getRoomQuantity());
        roomContractRegisterFormDTO.setStartDate(comboHasSeatAndHotelDTO.getStartDate());
        roomContractRegisterFormDTO.setEndDate(comboHasSeatAndHotelDTO.getEndDate());
        return roomContractRegisterFormDTO;
    }

    public ComboResponeDTO convertToComBoResponeDTO(Integer comboPage, SeatInformation seat, List <Hotel> hotels) {
        ComboResponeDTO comboResponeDTO =
                new ComboResponeDTO();
        List <UnitComboResponeDTO>
                unitComboResponeDTOs =
                new ArrayList <>();
        for (Hotel hotel : hotels) {
            UnitComboResponeDTO
                    unitComboResponDTO =
                    new UnitComboResponeDTO();
            unitComboResponDTO.setSeat(seat);
            unitComboResponDTO.setHotel(hotel);
            unitComboResponDTO.setOriginPrice(seat.getUnitPrice() + hotel.getMinSellPrice());
            unitComboResponDTO.setComboPrice((int) ((seat.getUnitPrice() + hotel.getMinSellPrice()) * 0.9));
            unitComboResponeDTOs.add(unitComboResponDTO);
        }
        comboResponeDTO.setUnitComboResponDTOs(unitComboResponeDTOs);
        comboResponeDTO.setPage(comboPage);
        return comboResponeDTO;
    }

    public UpdateProfileCustomerResponseDTO convertToResponseDTO(Customer customer) {
        UpdateProfileCustomerResponseDTO responseDTO = new UpdateProfileCustomerResponseDTO();
        responseDTO.setCustomerId(customer.getId());
        responseDTO.setName(customer.getName());
        responseDTO.setGender(customer.getGender());
        responseDTO.setDate(customer.getDateOfBirth().getDayOfMonth());
        responseDTO.setMonth(customer.getDateOfBirth().getMonthValue());
        responseDTO.setYear(customer.getDateOfBirth().getYear());
        return responseDTO;
    }
}
