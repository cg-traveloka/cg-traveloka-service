package com.cgtravelokaservice.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AirplaneBrandDto implements Serializable {
    @NotBlank

    private String name;

    private MultipartFile logoImg;
}
