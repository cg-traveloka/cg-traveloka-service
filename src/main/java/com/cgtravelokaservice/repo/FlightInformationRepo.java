package com.cgtravelokaservice.repo;

import com.cgtravelokaservice.entity.airplant.FlightInformation;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface FlightInformationRepo extends JpaRepository<FlightInformation, Integer>{
//    @Query(value = "SELECT * FROM flight_information " +
//            "inner join where air_port_location where" +
//            )
//    Slice<FlightInformation> search()
}
