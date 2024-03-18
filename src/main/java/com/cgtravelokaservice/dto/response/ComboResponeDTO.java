package com.cgtravelokaservice.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ComboResponeDTO {
    private List <UnitComboResponeDTO>
            unitComboResponDTOs;
    private Integer page;
}
