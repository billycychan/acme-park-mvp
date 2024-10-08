package com.billycychan.acmepark.gate_access_service.ports.inbound;

import com.billycychan.acmepark.gate_access_service.dto.AccessResult;

public interface ResponseReceiver {
    void receive(AccessResult result);
}
