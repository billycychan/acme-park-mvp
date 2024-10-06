package com.billycychan.acmepark.permit_service.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor
public class TransponderAccessRequest {
    private String transponderId;
    private String licencePlate;
    private String parkingLot;
    private String gate;
}