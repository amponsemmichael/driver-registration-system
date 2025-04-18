package com.amponsem.driver_registration_system.domain.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class DriverResponse {
    // Getters and Setters
    private Long id;
    private String fullName;
    private String email;
    private String phone;
    private String truckType;
    private String documentName;
    private String documentDownloadUrl;

    // Constructor
    public DriverResponse(Long id, String fullName, String email, String phone,
                          String truckType, String documentName, String documentDownloadUrl) {
        this.id = id;
        this.fullName = fullName;
        this.email = email;
        this.phone = phone;
        this.truckType = truckType;
        this.documentName = documentName;
        this.documentDownloadUrl = documentDownloadUrl;
    }

}