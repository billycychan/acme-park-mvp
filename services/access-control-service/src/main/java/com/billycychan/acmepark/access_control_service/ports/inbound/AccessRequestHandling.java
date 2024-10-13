package com.billycychan.acmepark.access_control_service.ports.inbound;

import com.billycychan.acmepark.access_control_service.dto.AccessResult;
import com.billycychan.acmepark.access_control_service.dto.TransponderAccess;

public interface AccessRequestHandling {
    void receiveTransponderEntryRequest(TransponderAccess access);
    void receiveTransponderValidatedResult(AccessResult result);
}
