package com.billycychan.acmepark.gate_access_service.dto;

import lombok.Data;

@Data
public class AccessResult {
    private String transponderId;
    private String gateId;
    private AccessStatus accessStatus;
    private String message;
}
