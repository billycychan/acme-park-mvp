package com.billycychan.acmepark.gate_access_service.adapters;

import com.billycychan.acmepark.gate_access_service.dto.AccessResult;
import com.billycychan.acmepark.gate_access_service.dto.PermitValidatedEvent;
import com.billycychan.acmepark.gate_access_service.ports.outbound.GateManagement;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Slf4j
@Component
public class GateManager implements GateManagement {
    @Override
    public void handle(AccessResult result) {
        if (Objects.requireNonNull(result.getEvent()) == PermitValidatedEvent.VALIDATED) {
            openGate(result.getLot(), result.getGate());
        } else {
            showError(result.getLot(), result.getGate(), result.getMessage());
        }
    }

    private void openGate(String lot, String gate) {
        log.info("Opening the gate {} lot {}", gate, lot);
    }

    private void showError(String lot, String gate, String message) {
        log.error("I am sorry access denied at the gate {} lot {}", gate, lot);
    }
}
