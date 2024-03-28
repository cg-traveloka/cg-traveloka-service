package com.cgtravelokaservice.dto.response;

import lombok.Data;

@Data
public class HotelRegisterResponse {
    private Integer id;
    private String name;
    private Integer partnerId;

    public HotelRegisterResponse(Integer id, String name, Integer partnerId) {
        this.id = id;
        this.name = name;
        this.partnerId = partnerId;
    }
}
