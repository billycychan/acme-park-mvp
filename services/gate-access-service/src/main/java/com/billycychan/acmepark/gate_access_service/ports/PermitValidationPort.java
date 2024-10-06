package com.billycychan.acmepark.gate_access_service.ports;

import com.billycychan.acmepark.gate_access_service.dto.TransponderAccessRequest;

public interface PermitValidationPort {
    void isPermitValid(TransponderAccessRequest transponderAccessRequest);
}

