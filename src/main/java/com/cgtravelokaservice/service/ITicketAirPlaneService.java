package com.cgtravelokaservice.service;

import com.cgtravelokaservice.dto.TicketAirPlaneDTO;

public interface ITicketAirPlaneService {
    boolean bookFlight(TicketAirPlaneDTO ticketDTO);
}
