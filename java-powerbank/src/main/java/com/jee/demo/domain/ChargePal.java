package com.jee.demo.domain;


import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ChargePal {
    @JsonProperty("pId")
    private String pId;
    private String brand;
    private double price;
    private double total;
    private String img;
    private Integer status;
    private String dpId;

}
