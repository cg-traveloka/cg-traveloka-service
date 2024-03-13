package com.cgtravelokaservice.service.implement;

import com.cgtravelokaservice.dto.TicketAirplaneDto;

import com.cgtravelokaservice.entity.airplant.SeatInformation;
import com.cgtravelokaservice.repo.SeatInformationRepo;
import com.cgtravelokaservice.repo.TicketAirPlaneRepo;
import com.cgtravelokaservice.util.implement.ConvertUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TicketAirplaneService {
    @Autowired
    private TicketAirPlaneRepo ticketAirplaneRepo;

    @Autowired
    private SeatInformationRepo seatInformationRepo;

    @Autowired
    private ConvertUtil convertUtil;

    public boolean add(TicketAirplaneDto ticketAirplaneDto) {
        SeatInformation seatInformation = seatInformationRepo.getReferenceById(ticketAirplaneDto.getSeatInfoId());
        if (availableTicket(seatInformation, ticketAirplaneDto.getQuantity())) {
            ticketAirplaneRepo.save(convertUtil.ticketAirPlantDtoToTicketAirPlant(ticketAirplaneDto));
            seatInformation.setQuantity(seatInformation.getQuantity() - ticketAirplaneDto.getQuantity());
            seatInformationRepo.save(seatInformation);
            return true;
        } else {
            return false;
        }
    }

    private boolean availableTicket(SeatInformation seatInformation, Integer quantity) {
        return seatInformation.getQuantity() >= quantity;
    }
}
