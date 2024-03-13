package com.cgtravelokaservice.repo;

import com.cgtravelokaservice.entity.booking.TicketAirPlant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TicketAirplaneRepo extends JpaRepository<TicketAirPlant, Integer> {
}
