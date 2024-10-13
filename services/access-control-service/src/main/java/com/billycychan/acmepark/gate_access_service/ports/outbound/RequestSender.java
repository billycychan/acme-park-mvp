package com.billycychan.acmepark.gate_access_service.ports.outbound;

import com.billycychan.acmepark.gate_access_service.dto.TransponderAccessRequest;

public interface RequestSender {
    void send(TransponderAccessRequest request);
}
