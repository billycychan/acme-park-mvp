package com.billycychan.acmepark.permit_service.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
// {"action":"FINISH_TP_VALIDATION","payload":{"transponderId":"T_001","status":"VALID","lot":"A","gate":"E1"}}
@Data
public class AccessResult {
        String transponderId;
        PermitStatus status;
        String lot;
        String gate;
}
