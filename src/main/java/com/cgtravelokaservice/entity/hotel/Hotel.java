package com.cgtravelokaservice.entity.hotel;

import com.cgtravelokaservice.entity.city.City;
import com.cgtravelokaservice.entity.user.Partner;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Hotel implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @ManyToOne
    @JoinColumn(name = "partner_id", referencedColumnName = "id")
    private Partner partner;
    private String hotelName;
    private String description;
    private double hotelStar;
    private Integer hotelBookedNumbers;
    private String address;
    private Integer minOriginPrice;
    private Integer minSellPrice;
    private Double averagePoint;
    private String defaultImg;
    @ManyToOne
    @JoinColumn(name = "city_id", referencedColumnName = "id")
    private City city;
}
