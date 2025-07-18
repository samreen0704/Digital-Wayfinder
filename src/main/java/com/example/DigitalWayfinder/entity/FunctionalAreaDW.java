package com.example.DigitalWayfinder.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Builder;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "DWUserFunctionalArea")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FunctionalAreaDW {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "UserID", nullable = false)
    private String userId;
    
    @Column(name = "SessionID", nullable = false)
    private String sessionId;
    
    @Column(name = "FunctionalArea", nullable = false)
    private String functionalArea;
    
    @Column(name = "IndustryType")
    private String industryType;
    
    @Column(name = "FunctionalSubArea")
    private String functionalSubArea;
    
    // @Column(name = "CreatedAt")
    // private LocalDateTime createdAt;
    
    // @PrePersist
    // protected void onCreate() {
    //     createdAt = LocalDateTime.now();
    // }
}