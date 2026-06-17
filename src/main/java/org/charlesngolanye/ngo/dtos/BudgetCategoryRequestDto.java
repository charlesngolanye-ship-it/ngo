package org.charlesngolanye.ngo.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class BudgetCategoryRequestDto {
    private String name;
    private String description;
}
