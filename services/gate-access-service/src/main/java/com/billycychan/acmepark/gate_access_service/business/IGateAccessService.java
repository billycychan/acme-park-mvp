package com.billycychan.acmepark.gate_access_service.business;

import com.billycychan.acmepark.gate_access_service.dto.AccessRequest;


public interface IGateAccessService {
    void validateAccess(AccessRequest accessRequest);
}
