package com.cgtravelokaservice.repo;

import com.cgtravelokaservice.entity.booking.TicketAirPlant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface TicketAirPlaneRepo extends JpaRepository<TicketAirPlant,Integer> {
    @Query("SELECT COALESCE(SUM(t.quantity), 0) FROM TicketAirPlant t " +
            "WHERE t.flightInformation.id = :flightInformationId AND t.seatType.id = :seatTypeId")
    Integer sumQuantityByFlightAndSeatType(@Param("flightInformationId") Integer flightInformationId,
                                           @Param("seatTypeId") Integer seatTypeId);
}
