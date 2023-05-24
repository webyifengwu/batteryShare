package com.jee.demo.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class DropPointDto {
    private String dpId;
    private String dpName;
    private String dpZone;
    private Integer dpStock;
    private Integer dpAll;
    private Integer Distance;


}
