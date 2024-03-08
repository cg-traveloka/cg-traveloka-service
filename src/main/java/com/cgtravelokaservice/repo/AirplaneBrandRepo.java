package com.cgtravelokaservice.repo;

import com.cgtravelokaservice.entity.airplant.AirPlantBrand;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AirplaneBrandRepo extends JpaRepository<AirPlantBrand, Integer> {
}
