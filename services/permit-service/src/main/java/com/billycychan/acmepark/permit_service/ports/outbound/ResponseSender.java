package com.billycychan.acmepark.permit_service.ports.outbound;

import com.billycychan.acmepark.permit_service.dto.AccessResult;

public interface ResponseSender {
    void publishAccessResult(AccessResult result);
}
