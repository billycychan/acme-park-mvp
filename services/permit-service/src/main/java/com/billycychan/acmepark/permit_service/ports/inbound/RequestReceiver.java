package com.billycychan.acmepark.permit_service.ports.inbound;

import com.billycychan.acmepark.permit_service.dto.TransponderAccessRequest;

public interface RequestReceiver {
    void receive (TransponderAccessRequest accessRequest);
}
