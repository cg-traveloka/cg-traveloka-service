package com.cgtravelokaservice.repo;

import com.cgtravelokaservice.entity.airplant.AirPortLocation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AirportLocationRepo extends JpaRepository<AirPortLocation, Integer> {

    @Query("SELECT ap from AirPortLocation ap "+
            "JOIN ap.city ci "+
            "WHERE ci.name LIKE CONCAT('%', :name, '%')")
    List<AirPortLocation> getAirPortLocationByCity_Name(@Param("name") String city_name);

}
