package com.cgtravelokaservice.repo;

import com.cgtravelokaservice.entity.airplant.AirPortLocation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AirportLocationRepo extends JpaRepository <AirPortLocation, Integer> {

    @Query("SELECT ap from AirPortLocation ap " + "WHERE ap.city.id = :cityId")
    List <AirPortLocation> getAirPortLocationByCityId(@Param("cityId") Integer cityId);
    List<AirPortLocation> findByCityId(Integer cityId);


}
