package com.innovators.jobreferralportal.entity;


import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReferredCandidate {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long referralId;
    private String firstName;
    private String lastName;
    private int yearsOfExp;
    private Long referredBy;
    private String status;
    @Lob
    private byte[] resume; // This will store the PDF file



}
