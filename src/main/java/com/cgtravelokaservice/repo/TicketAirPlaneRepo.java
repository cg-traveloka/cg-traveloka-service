package com.cgtravelokaservice.repo;

import com.cgtravelokaservice.entity.booking.TicketAirPlant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface TicketAirPlaneRepo extends JpaRepository<TicketAirPlant, Integer> {
    List<TicketAirPlant> findAllByCustomerId(Integer id);
}

