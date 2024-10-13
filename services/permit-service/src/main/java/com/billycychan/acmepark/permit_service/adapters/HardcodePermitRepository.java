package com.billycychan.acmepark.permit_service.adapters;

import com.billycychan.acmepark.permit_service.config.PermitGenerator;
import com.billycychan.acmepark.permit_service.dto.AccessResult;
import com.billycychan.acmepark.permit_service.dto.Permit;
import com.billycychan.acmepark.permit_service.dto.PermitStatus;
import com.billycychan.acmepark.permit_service.dto.TransponderAccess;
import com.billycychan.acmepark.permit_service.ports.outbound.PermitRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class HardcodePermitRepository implements PermitRepository {

    private final List<Permit> permits;

    public HardcodePermitRepository() {
        this.permits = new ArrayList<>();
        this.permits.addAll(PermitGenerator.generatePermits());
    }

    public AccessResult validateTransponderRequest(TransponderAccess access) {
        AccessResult result = new AccessResult();
        PermitStatus status = findByTransponderId(access.getTransponderId())
                .filter(permit -> !isExpired(permit, access))
                .map(permit -> PermitStatus.VALID)
                .orElse(PermitStatus.NOT_VALID);
        result.setTransponderId(access.getTransponderId());
        result.setGate(access.getGate());
        result.setLot(access.getLot());
        result.setStatus(status);
        return result;
    }

    private Optional<Permit> findByTransponderId(String transponderId) {
        return permits.stream()
                .filter(permit -> permit.getTransponderId().equals(transponderId))
                .findFirst();
    }

    private boolean isExpired(Permit permit, TransponderAccess access) {
        return access.getTimestamp().after(permit.getExpiryDate());
    }
}