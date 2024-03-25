package com.cgtravelokaservice.controller;

import com.cgtravelokaservice.dto.TicketAirPlaneDTO;
import com.cgtravelokaservice.dto.request.ComboHasSeatAndHotelDTO;
import com.cgtravelokaservice.dto.request.ComboHasSeatDTO;
import com.cgtravelokaservice.dto.request.ComboRequestDTO;
import com.cgtravelokaservice.dto.request.RoomContractRegisterFormDTO;
import com.cgtravelokaservice.dto.response.BookingResponseDTO;
import com.cgtravelokaservice.dto.response.ComboResponeDTO;
import com.cgtravelokaservice.dto.response.HotelsResponseDTO;
import com.cgtravelokaservice.entity.airplant.SeatInformation;
import com.cgtravelokaservice.entity.booking.Combo;
import com.cgtravelokaservice.entity.booking.RoomContract;
import com.cgtravelokaservice.entity.booking.TicketAirPlant;
import com.cgtravelokaservice.entity.hotel.Hotel;
import com.cgtravelokaservice.repo.ComboRepo;
import com.cgtravelokaservice.repo.FlightInformationRepo;
import com.cgtravelokaservice.repo.RoomContractRepo;
import com.cgtravelokaservice.repo.RoomRepo;
import com.cgtravelokaservice.repo.SeatInformationRepo;
import com.cgtravelokaservice.repo.TicketAirPlaneRepo;
import com.cgtravelokaservice.service.IComboService;
import com.cgtravelokaservice.service.IFlightInformationService;
import com.cgtravelokaservice.service.IFlightService;
import com.cgtravelokaservice.service.IHotelService;
import com.cgtravelokaservice.service.IRoomContractService;
import com.cgtravelokaservice.service.ISeatService;
import com.cgtravelokaservice.service.implement.TicketAirPlaneService;
import com.cgtravelokaservice.util.IConvertUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@RestController
public class ComboController {
    @Autowired
    IFlightService flightService;
    @Autowired
    IFlightInformationService
            flightInformationService;
    @Autowired
    IHotelService hotelService;
    @Autowired
    FlightInformationRepo flightInformationRepo;
    @Autowired
    IConvertUtil convertUtil;
    @Autowired
    ISeatService seatService;
    @Autowired
    SeatInformationRepo seatInformationRepo;
    @Autowired
    IComboService comboService;
    @Autowired
    IRoomContractService roomContractService;
    @Autowired
    RoomRepo roomRepo;
    @Autowired
    RoomContractRepo roomContractRepo;
    @Autowired
    TicketAirPlaneRepo ticketAirPlaneRepo;
    @Autowired
    TicketAirPlaneService ticketAirPlaneService;
    @Autowired
    ComboRepo comboRepo;

    @GetMapping(value = "/api/combo/search")
    public ResponseEntity<?> searchCombo(@Validated @RequestBody ComboRequestDTO comboRequestDTO) {
        Integer comboPage = comboRequestDTO.getPage();
        if (comboPage == null) {
            comboPage = 0;
        }
        try {
            List<SeatInformation> seats = seatService.findAllAvailableSeatByRequest(comboRequestDTO.getSearchFlightDetailsRequestDTO(), comboPage);
            if (seats.isEmpty()) {
                throw new NoSuchElementException();
            }
            SeatInformation seat = seats.getFirst();
            comboRequestDTO.getHotelSearchDTO().setPageNumber(comboPage);
            HotelsResponseDTO hotelsResponseDTO = hotelService.search(comboRequestDTO.getHotelSearchDTO());
            List<Hotel> hotels = hotelsResponseDTO.getHotels();
            if (hotels.isEmpty()) {
                throw new NoSuchElementException();
            }
            ComboResponeDTO comboResponeDTO = convertUtil.convertToComBoResponeDTO(comboPage, seat, hotels);
            return ResponseEntity.ok().body(comboResponeDTO);
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Không có combo phù hợp");
        }
    }

    @GetMapping(value = "/api/combo/flight")
    public ResponseEntity<?> setFlightInCombo(@RequestBody ComboHasSeatDTO comboHasSeatDTO) {
        Integer comboPage = comboHasSeatDTO.getPage();
        if (comboPage == null) {
            comboPage = 0;
        }
        SeatInformation seat = seatInformationRepo.getReferenceById(comboHasSeatDTO.getSeatId());
        comboHasSeatDTO.getHotelSearchDTO().setPageNumber(comboPage);
        HotelsResponseDTO hotelsResponseDTO = hotelService.search(comboHasSeatDTO.getHotelSearchDTO());
        List<Hotel> hotels = hotelsResponseDTO.getHotels();
        ComboResponeDTO comboResponeDTO = convertUtil.convertToComBoResponeDTO(comboPage, seat, hotels);
        return ResponseEntity.ok().body(comboResponeDTO);
    }

    @GetMapping(value = "/api/combo")
    public ResponseEntity<?> previewCombo(@RequestBody ComboHasSeatAndHotelDTO comboHasSeatAndHotelDTO) {
        Combo combo = new Combo();
//        Tạo room contract
        RoomContractRegisterFormDTO roomContractRegisterFormDTO = convertUtil.convertToRoomContractRegisterFormDTO(comboHasSeatAndHotelDTO);
        RoomContract roomContract = convertUtil.roomContractFormDTOToRoomContract(roomContractRegisterFormDTO);
        combo.setRoomContract(roomContract);
//      Tạo ticket máy bay
        TicketAirPlaneDTO ticketAirPlaneDTO = new TicketAirPlaneDTO();
        ticketAirPlaneDTO.setSeatInfoId(comboHasSeatAndHotelDTO.getSeatId());
        ticketAirPlaneDTO.setQuantity(comboHasSeatAndHotelDTO.getSeatQuantity());
        TicketAirPlant ticketAirPlant = convertUtil.convertToTicketAirPlant(ticketAirPlaneDTO, seatInformationRepo.getReferenceById(comboHasSeatAndHotelDTO.getSeatId()));
        combo.setTicketAirPlant(ticketAirPlant);
        combo.setTotalMoney((int) ((roomContract.getTotalMoney() + ticketAirPlant.getTotalMoney()) * 0.9));
        return ResponseEntity.ok().body(combo);
    }

    @PostMapping(value = "/api/combo")
    public ResponseEntity<?> createCombo(@RequestBody ComboHasSeatAndHotelDTO comboHasSeatAndHotelDTO) {
        Combo combo = new Combo();
//        Tạo room contract
        RoomContractRegisterFormDTO roomContractRegisterFormDTO = convertUtil.convertToRoomContractRegisterFormDTO(comboHasSeatAndHotelDTO);
        RoomContract roomContract = convertUtil.roomContractFormDTOToRoomContract(roomContractRegisterFormDTO);
        combo.setRoomContract(roomContract);
//      Tạo ticket máy bay
        TicketAirPlaneDTO ticketAirPlaneDTO = new TicketAirPlaneDTO();
        ticketAirPlaneDTO.setSeatInfoId(comboHasSeatAndHotelDTO.getSeatId());
        ticketAirPlaneDTO.setQuantity(comboHasSeatAndHotelDTO.getSeatQuantity());
        TicketAirPlant ticketAirPlant = convertUtil.convertToTicketAirPlant(ticketAirPlaneDTO, seatInformationRepo.getReferenceById(comboHasSeatAndHotelDTO.getSeatId()));
        combo.setTicketAirPlant(ticketAirPlant);
        combo.setTotalMoney((int) ((roomContract.getTotalMoney() + ticketAirPlant.getTotalMoney()) * 0.9));
        boolean condition1 = roomContractService.isContractValid(roomContract);
        TicketAirPlant ticketAirPlant1 = ticketAirPlaneService.bookFlightAndGetTicket(ticketAirPlaneDTO);
        boolean condition2 = ticketAirPlant1 != null;
        if (condition1 && condition2) {
            RoomContract roomContract1 = roomContractRepo.save(roomContract);
            combo.setTicketAirPlant(ticketAirPlant1);
            combo.setRoomContract(roomContract1);
            combo = comboService.saveCombo(combo);
            return ResponseEntity.ok().body(combo);
        } else
            return ResponseEntity.ok().body("Đăng ký combo không thành công");
    }

    @GetMapping(value = "/api/comboPending/customer/{customerId}")
    public ResponseEntity<?> getCombosPendingByCustomer(@PathVariable Integer customerId) {
        List<Combo> combos = comboRepo.findAllByCustomerIdAndStatus(customerId, "pending");
        if (!combos.isEmpty()) {
            List<BookingResponseDTO> bookingResponses = new ArrayList<>();
            for (Combo combo : combos) {
                BookingResponseDTO bookingResponse = new BookingResponseDTO();
                bookingResponse.setHotelName(combo.getRoomContract().getRoom().getHotel().getHotelName());
                bookingResponse.setFlightNameFromCity(combo.getTicketAirPlant().getFlightInformation().getFromAirPortLocation().getCity().getName());
                bookingResponse.setFlightNameToCity(combo.getTicketAirPlant().getFlightInformation().getToAirPortLocation().getCity().getName());
                bookingResponse.setTotalMoney(combo.getTotalMoney());
                bookingResponse.setStatus("Đang chờ thanh toán");
                bookingResponses.add(bookingResponse);
            }
            return ResponseEntity.ok().body(bookingResponses);
        }
        return ResponseEntity.ok().body("Không tìm thấy combo nào của khách hàng này");
    }

    @GetMapping(value = "/api/comboBooked/customer/{customerId}")
    public ResponseEntity<?> getCombosBookedByCustomer(@PathVariable Integer customerId) {
        List<Combo> combos = comboRepo.findAllByCustomerIdAndStatus(customerId, "booked");
        if (!combos.isEmpty()) {
            List<BookingResponseDTO> bookingResponses = new ArrayList<>();
            for (Combo combo : combos) {
                BookingResponseDTO bookingResponse = new BookingResponseDTO();
                bookingResponse.setHotelName(combo.getRoomContract().getRoom().getHotel().getHotelName());
                bookingResponse.setFlightNameFromCity(combo.getTicketAirPlant().getFlightInformation().getFromAirPortLocation().getCity().getName());
                bookingResponse.setFlightNameToCity(combo.getTicketAirPlant().getFlightInformation().getToAirPortLocation().getCity().getName());
                bookingResponse.setTotalMoney(combo.getTotalMoney());
                bookingResponse.setStatus("Đã thanh toán");
                bookingResponses.add(bookingResponse);
            }
            return ResponseEntity.ok().body(bookingResponses);
        }
        return ResponseEntity.ok().body("Không tìm thấy combo nào của khách hàng này");
    }
}
