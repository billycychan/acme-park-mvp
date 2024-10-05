package com.billycychan.acmepark.gate_access_service.business;

import com.billycychan.acmepark.gate_access_service.dto.AccessRequest;
import com.billycychan.acmepark.gate_access_service.dto.AccessResult;
import com.billycychan.acmepark.gate_access_service.dto.AccessStatus;
import com.billycychan.acmepark.gate_access_service.ports.AccessLogPort;
import com.billycychan.acmepark.gate_access_service.ports.AccessValidationPort;
import com.billycychan.acmepark.gate_access_service.ports.GateControlPort;
import org.springframework.stereotype.Service;

@Service
public class GateAccessServiceImp implements IGateAccessService {

    private final AccessValidationPort accessValidationPort;
    private final GateControlPort gateControlPort;
    private final AccessLogPort accessLogPort;

    public GateAccessServiceImp(AccessValidationPort accessValidationPort,
                                GateControlPort gateControlPort,
                                AccessLogPort accessLogPort) {
        this.accessValidationPort = accessValidationPort;
        this.gateControlPort = gateControlPort;
        this.accessLogPort = accessLogPort;
    }

    @Override
    public void validateAccess(AccessRequest accessRequest) {
        AccessResult accessResult = accessValidationPort.validateAccess(accessRequest);
        if (accessResult.getAccessStatus().equals(AccessStatus.ACCESS_GRANTED)) {
            validateSuccess(accessResult);
        } else {
            validateFailure(accessResult);
        }
    }

    private void validateSuccess(AccessResult accessResult) {
        gateControlPort.openGate();
        accessLogPort.write(accessResult);
    }

    private void validateFailure(AccessResult accessResult) {
        accessLogPort.write(accessResult);
    }
}
