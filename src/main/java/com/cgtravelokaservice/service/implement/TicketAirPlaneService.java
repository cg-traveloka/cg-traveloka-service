package com.cgtravelokaservice.service.implement;


import com.cgtravelokaservice.dto.TicketAirPlaneDTO;
import com.cgtravelokaservice.entity.airplant.SeatInformation;
import com.cgtravelokaservice.entity.booking.TicketAirPlant;
import com.cgtravelokaservice.repo.SeatInformationRepo;
import com.cgtravelokaservice.repo.TicketAirPlaneRepo;
import com.cgtravelokaservice.service.ITicketAirPlaneService;
import com.cgtravelokaservice.util.IConvertUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TicketAirPlaneService implements ITicketAirPlaneService {
    @Autowired
    private SeatInformationRepo seatInformationRepo;


    @Autowired
    private IConvertUtil convertUtil;


    @Autowired
    private TicketAirPlaneRepo ticketAirPlantRepo;

    @Override
    public boolean bookFlight(TicketAirPlaneDTO ticketDTO) {
        SeatInformation seatInformation = getSeatInformationById(ticketDTO.getSeatInfoId());

        if (isSeatAvailable(seatInformation, ticketDTO.getQuantity())) {
            updateSeatInformation(seatInformation, ticketDTO.getQuantity());
            saveTicketAndSeatInformation(ticketDTO, seatInformation);
            return true;
        } else {
            return false;
        }
    }

    private SeatInformation getSeatInformationById(Integer seatInfoId) {
        return seatInformationRepo.getReferenceById(seatInfoId);
    }

    private boolean isSeatAvailable(SeatInformation seatInformation, int requestedQuantity) {
        return seatInformation.getQuantity() >= requestedQuantity;
    }

    private void updateSeatInformation(SeatInformation seatInformation, int bookedQuantity) {
        seatInformation.setQuantity(seatInformation.getQuantity() - bookedQuantity);
        seatInformationRepo.save(seatInformation);
    }

    private void saveTicketAndSeatInformation(TicketAirPlaneDTO ticketDTO, SeatInformation seatInformation) {
        TicketAirPlant ticket = convertUtil.convertToTicketAirPlant(ticketDTO, seatInformation);
        ticketAirPlantRepo.save(ticket);
    }


}
