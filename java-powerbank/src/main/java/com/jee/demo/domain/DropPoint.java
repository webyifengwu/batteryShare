package com.jee.demo.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class DropPoint {
    private String dpId;
    private String dpName;
    private String dpZone;
    private Integer dpStock;
    private Integer dpAll;

}
