package com.billycychan.acmepark.gate_access_service.ports.incoming;

import com.billycychan.acmepark.gate_access_service.dto.AccessResult;
import com.billycychan.acmepark.gate_access_service.dto.TransponderAccessRequest;

public interface GateAccessPort {
    AccessResult validateTransponderAccess(TransponderAccessRequest request);
}
