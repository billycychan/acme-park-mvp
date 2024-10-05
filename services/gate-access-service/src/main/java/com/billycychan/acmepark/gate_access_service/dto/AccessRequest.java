package com.billycychan.acmepark.gate_access_service.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Data @NoArgsConstructor
public class AccessRequest {
    private String transponderId;
    private String licencePlate;
    private String gateId;
}
