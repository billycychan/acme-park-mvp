package com.billycychan.acmepark.gate_access_service.ports;

import com.billycychan.acmepark.gate_access_service.dto.AccessRequest;

public interface AccessRequestPort {
    void requestAccess(AccessRequest accessRequest);
}
