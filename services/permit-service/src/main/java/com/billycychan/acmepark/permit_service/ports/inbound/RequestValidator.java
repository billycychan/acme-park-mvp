package com.billycychan.acmepark.permit_service.ports.inbound;

import com.billycychan.acmepark.permit_service.dto.AccessResult;
import com.billycychan.acmepark.permit_service.dto.TransponderAccessRequest;

public interface RequestValidator {
    AccessResult validateTransponderRequest(TransponderAccessRequest request);
}
