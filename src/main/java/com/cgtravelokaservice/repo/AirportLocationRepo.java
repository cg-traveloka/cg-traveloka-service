package com.cgtravelokaservice.repo;

import com.cgtravelokaservice.entity.airplant.AirPortLocation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AirportLocationRepo extends JpaRepository<AirPortLocation, Integer> {
    @Query(value = "SELECT ap.* FROM air_port_location AS ap " +
            "INNER JOIN city as ci ON ci.id = ap.city_id " +
            "WHERE ci.name LIKE CONCAT('%', :name,'%') ", nativeQuery = true)
    List<AirPortLocation> getAllByCity_Name(@Param("name") String cityName);
}
