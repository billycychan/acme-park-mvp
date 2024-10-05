package com.billycychan.acmepark.gate_access_service.ports;

import com.billycychan.acmepark.gate_access_service.dto.AccessResult;

public interface AccessLogPort {
    void write(AccessResult accessResult);
}
