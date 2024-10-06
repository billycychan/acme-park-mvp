package com.billycychan.acmepark.permit_service.ports;

import com.billycychan.acmepark.permit_service.dto.Permit;

import java.util.Optional;

public interface PermitRepository {
    Optional<Permit> findByTransponderId(String transponderId);
}
