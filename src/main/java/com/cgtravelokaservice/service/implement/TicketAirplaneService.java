package com.cgtravelokaservice.service.implement;

import com.cgtravelokaservice.dto.TicketAirplaneDto;
import com.cgtravelokaservice.entity.booking.TicketAirPlant;
import com.cgtravelokaservice.repo.TicketAirplaneRepo;
import com.cgtravelokaservice.util.implement.ConvertUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TicketAirplaneService {
    @Autowired
    private TicketAirplaneRepo ticketAirplaneRepo;

    @Autowired
    private ConvertUtil convertUtil;

    public void add(TicketAirplaneDto ticketAirplaneDto) {
        ticketAirplaneRepo.save(convertUtil.ticketAirPlantDtoToTicketAirPlant(ticketAirplaneDto));
    }
}
