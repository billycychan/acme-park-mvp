package com.billycychan.acmepark.gate_access_service.ports;

import com.billycychan.acmepark.gate_access_service.dto.AccessRequest;
import com.billycychan.acmepark.gate_access_service.dto.AccessResult;

public interface AccessValidationPort {
    AccessResult validateAccess(AccessRequest accessRequest);
}
