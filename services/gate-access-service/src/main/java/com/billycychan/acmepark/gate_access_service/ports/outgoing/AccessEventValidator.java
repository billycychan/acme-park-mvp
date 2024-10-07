package com.billycychan.acmepark.gate_access_service.ports.outgoing;

import com.billycychan.acmepark.gate_access_service.dto.AccessResult;

public interface AccessEventValidator {
    AccessResult validateTransponderAccess(String transponderId, String gate, String lot);
}
