package com.billycychan.acmepark.gate_access_service.adapters;

import com.billycychan.acmepark.gate_access_service.dto.AccessRequest;
import com.billycychan.acmepark.gate_access_service.dto.AccessResult;
import com.billycychan.acmepark.gate_access_service.dto.AccessStatus;
import com.billycychan.acmepark.gate_access_service.ports.AccessValidationPort;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Slf4j
@Service
public class AccessValidator implements AccessValidationPort {
    @Override
    public AccessResult validateAccess(AccessRequest accessRequest) {
        AccessRequest mockRequest = new AccessRequest();
        mockRequest.setTransponderId("T123");
        mockRequest.setGateId("M");
        mockRequest.setLicencePlate("ABCD1234");

        if (accessRequest.equals(mockRequest)) {
            return validateSuccess(accessRequest);
        } else {
            return validateFailure(accessRequest);
        }
    }

    private AccessResult validateSuccess(AccessRequest request) {
        return createAccessResult(request, AccessStatus.ACCESS_GRANTED, "Congratulations! ACCESS GRANTED!");
    }

    private AccessResult validateFailure(AccessRequest request) {
        return createAccessResult(request, AccessStatus.ACCESS_DENIED, "Oops! ACCESS DENIED!");
    }

    private AccessResult createAccessResult(AccessRequest request, AccessStatus status, String message) {
        AccessResult accessResult = new AccessResult();
        accessResult.setAccessStatus(status);
        accessResult.setTransponderId(request.getTransponderId());
        accessResult.setGateId(request.getGateId());
        accessResult.setMessage(message);
        return accessResult;
    }
}
