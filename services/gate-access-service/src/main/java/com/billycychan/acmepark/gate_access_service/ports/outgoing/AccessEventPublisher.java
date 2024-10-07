package com.billycychan.acmepark.gate_access_service.ports.outgoing;

import com.billycychan.acmepark.gate_access_service.dto.TransponderAccessRequest;

public interface AccessEventPublisher {
    void publish(TransponderAccessRequest request);
}
