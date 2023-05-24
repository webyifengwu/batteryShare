package com.jee.demo.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Order {
    private String orderId;
    private Double time;
    private Double total;
    private Integer status;
    @JsonProperty("pId")
    private String pId;
    private String userId;
    private String dpId;
    private String createTime;
    private String endTime;
}
