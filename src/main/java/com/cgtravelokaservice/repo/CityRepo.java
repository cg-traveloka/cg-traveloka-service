package com.cgtravelokaservice.repo;

import com.cgtravelokaservice.entity.city.City;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CityRepo extends JpaRepository <City, Integer> {
}
