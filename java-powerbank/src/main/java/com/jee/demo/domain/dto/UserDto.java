package com.jee.demo.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UserDto {
    private String userId;
    private String username;
    private String password;
    private String phone;
    private Double balance;
    private Integer role;
    private String avatar;
    private String lastLogin;
    private String zone;
    private String token;
}
