package com.innovators.jobreferralportal.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Job {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long jobId;
    private String positionName;
    private String jobDescription;
    private String departmentName;
    private String numberOfOpenPositions;
    private String location;

    public String getLocationData() {
        return location;
    }
}
