package com.jee.demo.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class User {
    private String userId;
    private String username;
    private String password;
    private String phone;
    private Double balance;
    private Integer role;
    private String avatar;
    private String lastLogin;
}
