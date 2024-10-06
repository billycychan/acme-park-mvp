package com.billycychan.acmepark.gate_access_service.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor
public class AccessRequest {
    private String transponderId;
    private String licencePlate;
    private String parkingLot;
    private String gate;
}
