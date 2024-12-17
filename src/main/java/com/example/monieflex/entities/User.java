package com.example.monieflex.entities;


import com.example.monieflex.enums.Roles;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String email;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String bvn;
    private String password;
    private String confirmPassword;
    @Enumerated(EnumType.STRING)
    private Roles roles;
    @UpdateTimestamp
    private LocalDateTime lastLogin;

    private BigDecimal walletBalance;

    private String otp;
    private LocalDateTime otpExpiryTime;

    @Column(name = "is_verified",nullable = false)
    private boolean isVerified = false;
}
