package com.billycychan.acmepark.permit_service.adapters;

import com.billycychan.acmepark.permit_service.dto.Permit;
import com.billycychan.acmepark.permit_service.dto.PermitGenerator;
import com.billycychan.acmepark.permit_service.ports.outbound.PermitRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class HardcodePermitRepository implements PermitRepository {

    private final List<Permit> permits;

    // Constructor
    public HardcodePermitRepository() {
        this.permits = new ArrayList<>();
        this.permits.addAll(PermitGenerator.generatePermits());
    }

    @Override
    public Optional<Permit> findByTransponderId(String transponderId) {
        // Find the permit by transponder ID
        return permits.stream()
                .filter(permit -> permit.getTransponderId().equals(transponderId))
                .findFirst();
    }
}