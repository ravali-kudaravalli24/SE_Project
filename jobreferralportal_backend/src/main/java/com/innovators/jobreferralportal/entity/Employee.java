package com.innovators.jobreferralportal.entity;


import com.innovators.jobreferralportal.enums.RoleEnum;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long employeeID;
    private String firstName;
    private String lastName;
    private String email;
    private String phone_number;
    @Enumerated(EnumType.STRING)
    private RoleEnum role;
    private String position;
    private String username;
    private String password;
    private Integer score;
    public Long getEmployeeID() {
        return employeeID;
    }

}
