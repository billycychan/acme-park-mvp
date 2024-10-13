package com.billycychan.acmepark.access_control_service.ports.outbound;

public interface GateControl {
    void openGate(String lot, String gate);
    void showError(String lot, String gate);
}
