package com.billycychan.acmepark.gate_access_service.ports.inbound;

import com.billycychan.acmepark.gate_access_service.dto.TransponderAccessRequest;

public interface RequestReceiver {
    void receive(TransponderAccessRequest request);
}
