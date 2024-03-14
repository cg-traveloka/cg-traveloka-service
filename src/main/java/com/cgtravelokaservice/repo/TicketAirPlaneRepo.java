package com.cgtravelokaservice.repo;

import com.cgtravelokaservice.entity.booking.TicketAirPlant;
import org.springframework.data.jpa.repository.JpaRepository;


public interface TicketAirPlaneRepo extends JpaRepository<TicketAirPlant,Integer> {

}