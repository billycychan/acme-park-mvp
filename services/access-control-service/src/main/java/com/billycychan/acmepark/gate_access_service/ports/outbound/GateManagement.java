package com.billycychan.acmepark.gate_access_service.ports.outbound;

import com.billycychan.acmepark.gate_access_service.dto.AccessResult;

public interface GateManagement {
    void handle(AccessResult result);
}
