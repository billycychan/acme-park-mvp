package com.billycychan.acmepark.permit_service.ports.outbound;

import com.billycychan.acmepark.permit_service.dto.AccessResult;
import com.billycychan.acmepark.permit_service.dto.TransponderAccess;

public interface PermitRepository {
    AccessResult validateTransponderRequest(TransponderAccess access);
}
