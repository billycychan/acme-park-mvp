package com.billycychan.acmepark.access_control_service.ports.outbound;

import com.billycychan.acmepark.access_control_service.dto.GateAccessBase;
import com.billycychan.acmepark.access_control_service.dto.TransponderAccess;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.sql.Timestamp;

public interface PermitValidation {
    void send(TransponderAccess access);
}
