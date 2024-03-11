package com.cgtravelokaservice.repo;

import com.cgtravelokaservice.entity.airplant.FlightInformation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FlightInformationRepo extends JpaRepository<FlightInformation, Integer>{
}
