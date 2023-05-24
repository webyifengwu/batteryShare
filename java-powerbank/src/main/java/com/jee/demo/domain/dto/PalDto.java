package com.jee.demo.domain.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class PalDto {
    @JsonProperty("pId")
    private String pId;
    private String brand;
    private double price;
    private double total;
    private String img;
    private Integer status;
    private String dpId;
    private String dpName;
}
