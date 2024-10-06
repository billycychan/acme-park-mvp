package com.billycychan.acmepark.gate_access_service.ports;

import com.billycychan.acmepark.gate_access_service.dto.AccessResult;
import com.billycychan.acmepark.gate_access_service.dto.TransponderAccessRequest;

public interface GateAccessUseCases {
    void processTransponder(TransponderAccessRequest transponderAccessRequest);
}
