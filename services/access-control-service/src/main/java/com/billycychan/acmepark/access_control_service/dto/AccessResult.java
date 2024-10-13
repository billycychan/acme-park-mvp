package com.billycychan.acmepark.access_control_service.dto;

import lombok.Data;


// {"action":"FINISH_TP_VALIDATION","payload":{"status":"VALID","lot":"A","gate":"E1"}}
@Data
public class AccessResult {
    String transponderId;
    PermitStatus status;
    String lot;
    String gate;
}
