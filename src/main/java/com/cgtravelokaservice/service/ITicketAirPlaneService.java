package com.cgtravelokaservice.service;

import com.cgtravelokaservice.dto.TicketAirPlaneDTO;
import com.cgtravelokaservice.entity.booking.TicketAirPlant;

public interface ITicketAirPlaneService {
    boolean bookFlight(TicketAirPlaneDTO ticketDTO);

    TicketAirPlant bookFlightAndGetTicket(TicketAirPlaneDTO ticketDTO);
}
