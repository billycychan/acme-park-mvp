package com.billycychan.acmepark.gate_access_service.business;

import com.billycychan.acmepark.gate_access_service.dto.AccessResult;
import com.billycychan.acmepark.gate_access_service.dto.TransponderAccessRequest;
import com.billycychan.acmepark.gate_access_service.ports.GateAccessUseCases;
import com.billycychan.acmepark.gate_access_service.ports.GateControlPort;
import com.billycychan.acmepark.gate_access_service.ports.PermitValidationPort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GateAccessService implements GateAccessUseCases {
    private final PermitValidationPort permitValidationPort;
    private final GateControlPort gateControlPort;

    @Autowired
    public GateAccessService(PermitValidationPort permitValidationPort, GateControlPort gateControlPort) {
        this.permitValidationPort = permitValidationPort;
        this.gateControlPort = gateControlPort;
    }

    @Override
    public void processTransponder(TransponderAccessRequest transponderAccessRequest) {
        permitValidationPort.isPermitValid(transponderAccessRequest);

        gateControlPort.openGate(transponderAccessRequest.getParkingLot(), transponderAccessRequest.getGate());
//        return new AccessResult(true, "Access granted");
//        return new AccessResult(false, "Access denied");
    }
}