package com.billycychan.acmepark.gate_access_service.ports;

import org.springframework.context.annotation.Bean;

public interface GateControlPort {
    void openGate(String parkingLot, String gate);
    void closeGate(String parkingLot, String gate);
}
