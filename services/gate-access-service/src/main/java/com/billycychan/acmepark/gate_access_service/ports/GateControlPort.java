package com.billycychan.acmepark.gate_access_service.ports;

import org.springframework.context.annotation.Bean;

public interface GateControlPort {
    void loadingGate(String gateId);
    void openGate(String gateId);
    void closeGate(String gateId);
}
