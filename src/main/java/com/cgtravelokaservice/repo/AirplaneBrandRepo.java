package com.cgtravelokaservice.repo;

import com.cgtravelokaservice.entity.airplant.AirPlantBrand;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AirplaneBrandRepo extends JpaRepository <AirPlantBrand, Integer> {
    @Query("SELECT a.id FROM AirPlantBrand a")
    List <Integer> findAllId();

    List<AirPlantBrand> findAllByNameContainsIgnoreCase(String name, Sort sort);


}
