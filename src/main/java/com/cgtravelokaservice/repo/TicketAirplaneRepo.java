package com.cgtravelokaservice.repo;

import com.cgtravelokaservice.entity.airplant.FlightInformation;
import com.cgtravelokaservice.entity.airplant.SeatType;
import com.cgtravelokaservice.entity.booking.TicketAirPlant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface TicketAirplaneRepo extends JpaRepository<TicketAirPlant, Integer> {
//    @Query(value = "select sum(quantity) from ticket_air_plant as ta " +
//            "where (ta.flight_id = :flightInfoId and ta.set_type_id = :seatTypeId) ", nativeQuery = true)
//    Integer getBookedSeatQuantity(@Param("flightInfoId") Integer flightInfoId,
//                                  @Param("seatTypeId") Integer seatTypeId);

    @Query(value = "select sum(ta.quantity) from TicketAirPlant as ta " +
            "where (ta.flightInformation = :flightInfo and ta.seatType = :seatType) ")
    Integer getBookedSeatQuantity_v02(@Param("flightInfo") FlightInformation flightInfo,
                                  @Param("seatType") SeatType seatType);
}
